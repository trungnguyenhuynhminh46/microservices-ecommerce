package com.tuber.inventory.service.domain.event;

import com.tuber.domain.event.DomainEvent;
import com.tuber.inventory.service.domain.entity.Warehouse;

public class WarehouseCreatedEvent implements DomainEvent<Warehouse> {
    private final Warehouse warehouse;

    public WarehouseCreatedEvent(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
}
