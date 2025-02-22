package com.tuber.inventory.service.domain;

import com.tuber.inventory.service.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.event.WarehouseCreatedEvent;

public interface InventoryDomainService {
    public WarehouseCreatedEvent validateAndInitializeWarehouse(Warehouse warehouse);
}
