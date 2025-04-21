package com.tuber.inventory.service.domain.mapper;

import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.event.InventoryConfirmationEvent;
import com.tuber.inventory.service.domain.outbox.model.OrderEventPayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FulfillmentHistoryMapper {
    //TODO: Implement this method
    public abstract FulfillmentHistory inventoryConfirmationRequestToFulfillmentHistory(
            InventoryConfirmationRequest inventoryConfirmationRequest);
    //TODO: Implement this method
    public abstract OrderEventPayload inventoryConfirmationEventToOrderEventPayload(
            InventoryConfirmationEvent inventoryConfirmationEvent
    );

}
