package com.tuber.inventory.service.domain.mapper;

import com.tuber.inventory.service.domain.dto.message.broker.ExportInformation;
import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import com.tuber.inventory.service.domain.dto.shared.ProductIdWithSkuDTO;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.entity.ProductFulfillment;
import com.tuber.inventory.service.domain.event.InventoryConfirmationEvent;
import com.tuber.inventory.service.domain.outbox.model.OrderEventPayload;
import com.tuber.inventory.service.domain.valueobject.enums.OrderInventoryConfirmationStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    //TODO: Implement this method
    public abstract OrderEventPayload inventoryConfirmationEventToOrderEventPayload(
            InventoryConfirmationEvent inventoryConfirmationEvent
    );

    //TODO: Implement this method
    public OrderInventoryConfirmationStatus productFulfillInformationToConfirmationStatus(
            Set<ProductFulfillment> productFulfillments
    ) {
        return null;
    }

    //TODO: Implement this method
    public Set<UUID> exportInformationToProductIds(
            List<ExportInformation> exportInformationList
    ) {
        return null;
    }

    //TODO: Implement this method
    public Set<ProductFulfillment> exportInformationListToProductFulfillments(
            List<ExportInformation> exportInformationList
    ) {
        return null;
    }

    //TODO: Implement this method
    public Set<ProductIdWithSkuDTO> exportInformationToProductIdWithSkuDTO(List<ExportInformation> exportInformationList) {
        return null;
    }
}
