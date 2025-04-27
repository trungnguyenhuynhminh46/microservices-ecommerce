package com.tuber.inventory.service.domain.mapper;

import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.domain.dto.message.broker.ExportInformation;
import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import com.tuber.inventory.service.domain.dto.shared.ProductIdWithSkuDTO;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.entity.ProductFulfillment;
import com.tuber.inventory.service.domain.event.InventoryConfirmationEvent;
import com.tuber.inventory.service.domain.outbox.model.OrderEventPayload;
import com.tuber.inventory.service.domain.valueobject.enums.OrderInventoryConfirmationStatus;
import com.tuber.inventory.service.domain.valueobject.enums.ProductFulfillStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class FulfillmentHistoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trackingId", ignore = true)
    @Mapping(target = "orderInventoryConfirmationStatus", source = "orderInventoryConfirmationStatus")
    @Mapping(target = "productFulfillments", source = "productFulfillments")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract FulfillmentHistory inventoryConfirmationRequestToFulfillmentHistory(
            InventoryConfirmationRequest inventoryConfirmationRequest,
            Set<ProductFulfillment> productFulfillments,
            OrderInventoryConfirmationStatus orderInventoryConfirmationStatus
    );

    @Mapping(target = "orderId", source = "inventoryConfirmationEvent.fulfillmentHistory.orderId")
    @Mapping(target = "fulfillHistoryId", source = "inventoryConfirmationEvent.fulfillmentHistory.id")
    @Mapping(target = "orderInventoryConfirmationStatus", source = "inventoryConfirmationEvent.fulfillmentHistory.orderInventoryConfirmationStatus")
    public abstract OrderEventPayload inventoryConfirmationEventToOrderEventPayload(
            InventoryConfirmationEvent inventoryConfirmationEvent,
            List<String> failureMessages
    );

    public OrderInventoryConfirmationStatus productFulfillInformationToConfirmationStatus(
            Set<ProductFulfillment> productFulfillments
    ) {
        return productFulfillments.stream()
                .anyMatch(productFulfillment -> productFulfillment.getFulfillStatus() == ProductFulfillStatus.REJECTED)
                ? OrderInventoryConfirmationStatus.FAILED
                : OrderInventoryConfirmationStatus.CONFIRMED;
    }

    public Set<UUID> exportInformationToProductIds(
            List<ExportInformation> exportInformationList
    ) {
        return exportInformationList.stream()
                .map(ExportInformation::getProductId)
                .collect(Collectors.toSet());
    }

    public Set<ProductFulfillment> exportInformationListToProductFulfillments(
            List<ExportInformation> exportInformationList
    ) {
        return exportInformationList.stream()
                .map(this::exportInformationToProductFulfillment)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fulfillmentHistoryId", ignore = true)
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "inventoryId", ignore = true)
    @Mapping(target = "quantity", source = "requiredQuantity")
    @Mapping(target = "fulfillStatus", constant = "APPROVED")
    protected abstract ProductFulfillment exportInformationToProductFulfillment(
            ExportInformation exportInformation
    );

    public Set<ProductIdWithSkuDTO> productFulfillmentToProductIdWithSkuDTO(Set<ProductFulfillment> productFulfillments) {
        return productFulfillments.stream()
                .map(productFulfillment -> ProductIdWithSkuDTO.builder()
                        .productId(productFulfillment.getProductId())
                        .sku(productFulfillment.getSku())
                        .build())
                .collect(Collectors.toSet());
    }

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }

    protected Money map(BigDecimal amount) {
        return new Money(amount);
    }
}
