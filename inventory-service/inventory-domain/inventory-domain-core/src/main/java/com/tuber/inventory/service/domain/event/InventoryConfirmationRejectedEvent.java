package com.tuber.inventory.service.domain.event;

import com.tuber.inventory.service.domain.entity.FulfillmentHistory;

import java.time.LocalDate;

public class InventoryConfirmationRejectedEvent extends InventoryConfirmationEvent {
    public InventoryConfirmationRejectedEvent(FulfillmentHistory fulfillmentHistory, LocalDate createdAt) {
        super(fulfillmentHistory, createdAt);
    }
}
