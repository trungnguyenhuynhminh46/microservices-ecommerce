package com.tuber.inventory.service.domain.helper;

import com.tuber.inventory.service.domain.InventoryDomainService;
import com.tuber.inventory.service.domain.dto.message.broker.ExportInformation;
import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import com.tuber.inventory.service.domain.dto.shared.ProductIdWithSkuDTO;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.entity.Product;
import com.tuber.inventory.service.domain.entity.ProductFulfillment;
import com.tuber.inventory.service.domain.event.InventoryConfirmationEvent;
import com.tuber.inventory.service.domain.helper.inventory.GetProductsRecord;
import com.tuber.inventory.service.domain.mapper.FulfillmentHistoryMapper;
import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import com.tuber.inventory.service.domain.outbox.scheduler.OrderOutboxHelper;
import com.tuber.inventory.service.domain.ports.output.message.publisher.InventoryConfirmationResponsePublisher;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryRepository;
import com.tuber.inventory.service.domain.valueobject.enums.ProductFulfillStatus;
import com.tuber.outbox.OutboxStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryConfirmationMessageHelper {
    InventoryDomainService inventoryDomainService;
    FulfillmentHistoryMapper fulfillmentHistoryMapper;
    CommonInventoryHelper commonInventoryHelper;
    OrderOutboxHelper orderOutboxHelper;
    InventoryConfirmationResponsePublisher inventoryConfirmationResponsePublisher;
    InventoryRepository inventoryRepository;

    @Transactional
    public void persistFulfillmentHistory(InventoryConfirmationRequest inventoryConfirmationRequest) {
        if (republishIfOutboxMessageExisted(inventoryConfirmationRequest.getSagaId())) {
            log.info("The outbox message with saga id: {} exists!",
                    inventoryConfirmationRequest.getSagaId());
            return;
        }
        log.info("Processing outbox message with saga id: {}",
                inventoryConfirmationRequest.getSagaId());

        List<String> failureMessages = new ArrayList<>();
        FulfillmentHistory history = this.generateFulfillmentHistory(inventoryConfirmationRequest, failureMessages);
        InventoryConfirmationEvent inventoryConfirmationEvent = inventoryDomainService.validateAndInitializeFulfillmentHistory(history, inventoryConfirmationRequest.getInventoryOrderStatus(), failureMessages);
        commonInventoryHelper.saveFulfillmentHistory(history);
        orderOutboxHelper.saveOrderOutboxMessage(
                orderOutboxHelper.createOrderOutboxMessage(
                        fulfillmentHistoryMapper.inventoryConfirmationEventToOrderEventPayload(
                                inventoryConfirmationEvent,
                                failureMessages
                        ),
                        inventoryConfirmationEvent.getFulfillmentHistory().getOrderInventoryConfirmationStatus(),
                        OutboxStatus.STARTED,
                        inventoryConfirmationRequest.getSagaId()
                )
        );
    }

    protected boolean republishIfOutboxMessageExisted(UUID sagaID) {
        Optional<OrderOutboxMessage> outboxMessage =
                orderOutboxHelper.findOrderOutboxMessageWithOutboxStatus(sagaID, OutboxStatus.COMPLETED);
        if (outboxMessage.isPresent()) {
            inventoryConfirmationResponsePublisher.publish(outboxMessage.get(), orderOutboxHelper::updateOutboxStatus);
            return true;
        }
        return false;
    }

    protected FulfillmentHistory generateFulfillmentHistory(
            InventoryConfirmationRequest inventoryConfirmationRequest,
            List<String> failureMessages
    ) {
        Set<ProductFulfillment> productFulfillments = validateExportInformation(inventoryConfirmationRequest.getExportInformationList(), failureMessages);
        return fulfillmentHistoryMapper.inventoryConfirmationRequestToFulfillmentHistory(
                inventoryConfirmationRequest,
                productFulfillments,
                fulfillmentHistoryMapper.productFulfillInformationToConfirmationStatus(
                        productFulfillments
                )
        );
    }

    protected Set<ProductFulfillment> validateExportInformation(
            List<ExportInformation> exportInformationList,
            List<String> failureMessages
    ) {
        Set<ProductFulfillment> productFulfillments = fulfillmentHistoryMapper.exportInformationListToProductFulfillments(
                exportInformationList
        );
        Set<UUID> productIds = fulfillmentHistoryMapper.exportInformationToProductIds(
                exportInformationList
        );
        Set<Product> products = validateProductsAreAvailable(productIds, failureMessages);
        validateExportInformationUpToDate(productFulfillments, products, failureMessages);
        validateThereAreEnoughStock(productFulfillments, failureMessages);

        return productFulfillments;
    }

    protected Set<Product> validateProductsAreAvailable(
            Set<UUID> productIds,
            List<String> failureMessages
    ) {
        GetProductsRecord record = commonInventoryHelper.getProductsDetails(productIds);
        if (record.hasUnavailableProducts()) {
            String unavailableProductIds = productIds.stream()
                    .filter(productId -> record.products().stream().noneMatch(
                            product -> product.getId().getValue().equals(productId)
                    ))
                    .map(UUID::toString)
                    .collect(Collectors.joining(", "));
            String errorMessage = String.format("Products with ids: %s are unavailable", unavailableProductIds);
            log.error(errorMessage);
            failureMessages.add(errorMessage);
        }
        return record.products();
    }

    protected void validateExportInformationUpToDate(
            Set<ProductFulfillment> productFulfillments,
            Set<Product> products,
            List<String> failureMessages
    ) {
        Set<ProductFulfillment> invalidFulfillments =
                productFulfillments.stream()
                        .filter(
                                fulfillment -> products.stream().noneMatch(
                                        product -> {
                                            Boolean isProductIdMatch = product.getId().getValue().equals(fulfillment.getProductId());
                                            Boolean isProductBasePriceMatch = product.getPrice().equals(fulfillment.getBasePrice());
                                            Boolean isSkuValid = product.validateSku(fulfillment.getSku());
                                            return isProductIdMatch && isProductBasePriceMatch && isSkuValid;
                                        }
                                )
                        )
                        .peek(invalidFulfillment -> invalidFulfillment.setFulfillStatus(ProductFulfillStatus.REJECTED))
                        .collect(Collectors.toSet());
        if (!invalidFulfillments.isEmpty()) {
            String invalidInformation = invalidFulfillments.stream()
                    .map(information -> String.format("{%s, %s}", information.getProductId(), information.getBasePrice()))
                    .collect(Collectors.joining(", "));
            String errorMessage = String.format("Export information is outdated, outdated product information: %s", invalidInformation);
            log.error(errorMessage);
            failureMessages.add(errorMessage);
        }
    }

    protected void validateThereAreEnoughStock(
            Set<ProductFulfillment> productFulfillments,
            List<String> failureMessages
    ) {
        Set<ProductIdWithSkuDTO> productIdWithSku = fulfillmentHistoryMapper.productFulfillmentToProductIdWithSkuDTO(
                productFulfillments
        );
        Set<Inventory> inventories = inventoryRepository.findAllByProductIdsAndSkusSet(productIdWithSku);
        inventories.forEach(
                inventory -> {
                    Optional<ProductFulfillment> matchedFulfillment = productFulfillments.stream()
                            .filter(fulfillment -> fulfillment.getProductId().equals(inventory.getProduct().getId().getValue()))
                            .findFirst();
                    if (matchedFulfillment.isEmpty()) {
                        String errorMessage = String.format("Product with id %s and sku %s is not found in inventory",
                                inventory.getProduct().getId().getValue(),
                                inventory.getSku()
                        );
                        log.error(errorMessage);
                        failureMessages.add(errorMessage);
                    }
                    Integer requiredQuantity = matchedFulfillment.get().getQuantity();
                    if (inventory.getStockQuantity() < requiredQuantity) {
                        String errorMessage = String.format("Product with id %s and sku %s is out of stock. Required entity: %s, current stock: %s",
                                inventory.getProduct().getId().getValue(),
                                matchedFulfillment.get().getSku(),
                                requiredQuantity,
                                inventory.getStockQuantity()
                        );
                        matchedFulfillment.get().setFulfillStatus(ProductFulfillStatus.REJECTED);
                        matchedFulfillment.get().setInventoryId(inventory.getId().getValue());
                        log.error(errorMessage);
                        failureMessages.add(errorMessage);
                    }
                }
        );
    }
}
