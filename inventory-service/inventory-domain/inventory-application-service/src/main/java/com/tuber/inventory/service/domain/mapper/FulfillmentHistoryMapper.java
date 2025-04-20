package com.tuber.inventory.service.domain.mapper;

import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FulfillmentHistoryMapper {
    //TODO: Implement this method
    public abstract FulfillmentHistory inventoryConfirmationRequestToFulfillmentHistory(
            com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest inventoryConfirmationRequest);
}
