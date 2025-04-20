package com.tuber.inventory.service.domain;

import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import com.tuber.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.event.InventoryCreatedEvent;
import com.tuber.inventory.service.domain.event.WarehouseCreatedEvent;

public interface InventoryDomainService {
    WarehouseCreatedEvent validateAndInitializeWarehouse(Warehouse warehouse);

    InventoryCreatedEvent validateAndInitializeInventory(Inventory inventory);

    InventoryTransaction validateAndInitializeInventoryTransaction(InventoryTransaction inventoryTransaction);

    FulfillmentHistory validateAndInitializeFulfillmentHistory(FulfillmentHistory fulfillmentHistory);
}
