package com.tuber.order.service.domain.mapper;

import com.tuber.order.service.domain.event.OrderPaymentCompleteEvent;
import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationEventPayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class OutboxMessageMapper {
    //TODO: Implement this method
    public abstract InventoryConfirmationEventPayload orderPaymentCompleteEventToInventoryConfirmationEventPayload(
            OrderPaymentCompleteEvent orderPaymentCompleteEvent
    );
}
