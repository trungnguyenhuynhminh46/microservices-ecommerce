package com.tuber.inventory.service.domain.helper;

import com.tuber.domain.valueobject.Money;
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
import com.tuber.outbox.OutboxStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

//TODO: Whenever throw an exception, update failure messages (OK), update state value ()
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
        InventoryConfirmationEvent inventoryConfirmationEvent = inventoryDomainService.validateAndInitializeFulfillmentHistory(history, failureMessages);
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
        Set<UUID> productIds = fulfillmentHistoryMapper.exportInformationToProductIds(
                exportInformationList
        );
        Set<Product> products = validateProductsAreAvailable(productIds, failureMessages);
        validateExportInformationUpToDate(exportInformationList, products, failureMessages);
        validateThereAreEnoughStock(exportInformationList, failureMessages);

        return fulfillmentHistoryMapper.exportInformationListToProductFulfillments(
                exportInformationList
        );
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
            List<ExportInformation> exportInformationList,
            Set<Product> products,
            List<String> failureMessages
    ) {
        List<ExportInformation> invalidExportInformationList =
                exportInformationList.stream()
                        .filter(
                                information -> products.stream().noneMatch(
                                        product -> product.getId().getValue().equals(information.getProductId())
                                                && product.getPrice().equals(new Money(information.getBasePrice()))
                                )
                        ).toList();
        if (!invalidExportInformationList.isEmpty()) {
            String invalidInformation = invalidExportInformationList.stream()
                    .map(information -> String.format("{%s, %s}", information.getProductId(), information.getBasePrice()))
                    .collect(Collectors.joining(", "));
            String errorMessage = String.format("Export information is outdated, outdated product information: %s", invalidInformation);
            log.error(errorMessage);
            failureMessages.add(errorMessage);
        }
    }

    protected void validateThereAreEnoughStock(
            List<ExportInformation> exportInformationList,
            List<String> failureMessages
    ) {
        Set<ProductIdWithSkuDTO> productIdWithSku = fulfillmentHistoryMapper.exportInformationToProductIdWithSkuDTO(
                exportInformationList
        );
        Set<Inventory> inventories = inventoryRepository.findAllByProductIdsAndSkusSet(productIdWithSku);
        inventories.forEach(
                inventory -> {
                    Optional<ExportInformation> exportInformation = exportInformationList.stream()
                            .filter(information -> information.getProductId().equals(inventory.getProduct().getId().getValue()))
                            .findFirst();
                    if (exportInformation.isEmpty()) {
                        String errorMessage = String.format("Something went wrong! export information for product with id %s is not found", inventory.getProduct().getId().getValue());
                        log.error(errorMessage);
                        failureMessages.add(errorMessage);
                    }
                    Integer requiredQuantity = exportInformation.get().getRequiredQuantity();
                    if (inventory.getStockQuantity() < requiredQuantity) {
                        String errorMessage = String.format("Product with id %s and sku %s is out of stock. Required entity: %s, current stock: %s",
                                inventory.getProduct().getId().getValue(),
                                exportInformation.get().getSku(),
                                requiredQuantity,
                                inventory.getStockQuantity()
                        );
                        log.error(errorMessage);
                        failureMessages.add(errorMessage);
                    }
                }
        );
    }
}
