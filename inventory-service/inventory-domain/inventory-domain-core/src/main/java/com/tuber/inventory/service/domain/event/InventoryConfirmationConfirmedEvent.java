package com.tuber.inventory.service.domain.event;

import com.tuber.inventory.service.domain.entity.FulfillmentHistory;

import java.time.LocalDate;

public class InventoryConfirmationConfirmedEvent extends InventoryConfirmationEvent{
    public InventoryConfirmationConfirmedEvent(FulfillmentHistory fulfillmentHistory, LocalDate createdAt) {
        super(fulfillmentHistory, createdAt);
    }
}
