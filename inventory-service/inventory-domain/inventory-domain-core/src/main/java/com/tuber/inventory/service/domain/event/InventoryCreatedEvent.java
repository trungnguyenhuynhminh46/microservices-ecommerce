package com.tuber.inventory.service.domain.event;

import com.tuber.domain.event.DomainEvent;
import com.tuber.inventory.service.domain.entity.Inventory;

public class InventoryCreatedEvent implements DomainEvent<Inventory> {
    private final Inventory inventory;

    public InventoryCreatedEvent(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
