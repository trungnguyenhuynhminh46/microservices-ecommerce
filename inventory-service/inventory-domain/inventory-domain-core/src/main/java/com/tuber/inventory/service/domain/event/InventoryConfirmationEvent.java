package com.tuber.inventory.service.domain.event;


import com.tuber.domain.event.DomainEvent;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;

import java.time.LocalDate;

public class InventoryConfirmationEvent implements DomainEvent<FulfillmentHistory> {
    private final FulfillmentHistory fulfillmentHistory;
    private final LocalDate createdAt;

    public InventoryConfirmationEvent(FulfillmentHistory fulfillmentHistory, LocalDate createdAt) {
        this.fulfillmentHistory = fulfillmentHistory;
        this.createdAt = createdAt;
    }

    public FulfillmentHistory getFulfillmentHistory() {
        return fulfillmentHistory;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
