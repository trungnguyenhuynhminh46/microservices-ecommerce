package com.tuber.inventory.service.domain.helper;

import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.domain.exception.InventoryDomainException;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

//TODO: Whenever throw an exception, update failure messages, then save an outbox message
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
        Set<ProductFulfillment> productFulfillments = validateExportInformation(inventoryConfirmationRequest.getExportInformationList());
        return fulfillmentHistoryMapper.inventoryConfirmationRequestToFulfillmentHistory(
                inventoryConfirmationRequest,
                productFulfillments,
                fulfillmentHistoryMapper.productFulfillInformationToConfirmationStatus(
                        productFulfillments
                )
        );
    }

    protected Set<Product> validateProductsAreAvailable(Set<UUID> productIds) {
        GetProductsRecord record = commonInventoryHelper.getProductsDetails(productIds);
        if (record.hasUnavailableProducts()) {
            Set<UUID> unavailableProductIds = productIds.stream()
                    .filter(productId -> record.products().stream().noneMatch(
                            product -> product.getId().getValue().equals(productId)
                    ))
                    .collect(Collectors.toSet());
            throw new InventoryDomainException(
                    InventoryResponseCode.THERE_IS_UNAVAILABLE_PRODUCTS,
                    HttpStatus.BAD_REQUEST.value(),
                    unavailableProductIds.stream().map(UUID::toString).collect(Collectors.joining(", "))
            );
        }
        return record.products();
    }

    protected void validateExportInformationUpToDate(
            List<ExportInformation> exportInformationList,
            Set<Product> products
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
            throw new InventoryDomainException(
                    InventoryResponseCode.OUTDATED_EXPORT_INFORMATION,
                    HttpStatus.BAD_REQUEST.value(),
                    invalidInformation
            );
        }
    }

    protected void validateThereAreEnoughStock(List<ExportInformation> exportInformationList) {
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
                        throw new InventoryDomainException(
                                new InventoryResponseCode(
                                        String.format("Something went wrong! export information for product with id %s is not found", inventory.getProduct().getId().getValue())),
                                HttpStatus.BAD_REQUEST.value()

                        );
                    }
                    Integer requiredQuantity = exportInformation.get().getRequiredQuantity();
                    if (inventory.getStockQuantity() < requiredQuantity) {
                        throw new InventoryDomainException(
                                InventoryResponseCode.NOT_ENOUGH_STOCK,
                                HttpStatus.BAD_REQUEST.value(),
                                inventory.getProduct().getId().getValue(),
                                exportInformation.get().getSku(),
                                requiredQuantity,
                                inventory.getStockQuantity()
                        );
                    }
                }
        );
    }

    protected Set<ProductFulfillment> validateExportInformation(List<ExportInformation> exportInformationList) {
        Set<UUID> productIds = fulfillmentHistoryMapper.exportInformationToProductIds(
                exportInformationList
        );
        Set<Product> products = validateProductsAreAvailable(productIds);
        validateExportInformationUpToDate(exportInformationList, products);
        validateThereAreEnoughStock(exportInformationList);

        return fulfillmentHistoryMapper.exportInformationListToProductFulfillments(
                exportInformationList
        );
    }
}
