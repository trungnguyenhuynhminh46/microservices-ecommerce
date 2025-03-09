package com.tuber.inventory.service.domain;

import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import com.tuber.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.event.InventoryCreatedEvent;
import com.tuber.inventory.service.domain.event.WarehouseCreatedEvent;

public interface InventoryDomainService {
    public WarehouseCreatedEvent validateAndInitializeWarehouse(Warehouse warehouse);

    public InventoryCreatedEvent validateAndInitializeInventory(Inventory inventory);

    public InventoryTransaction validateAndInitializeInventoryTransaction(InventoryTransaction inventoryTransaction);
}
