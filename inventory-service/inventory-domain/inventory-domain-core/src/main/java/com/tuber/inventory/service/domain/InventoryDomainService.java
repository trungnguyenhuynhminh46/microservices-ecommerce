package com.tuber.inventory.service.domain;

import com.tuber.domain.valueobject.enums.InventoryOrderStatus;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import com.tuber.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.event.InventoryConfirmationEvent;
import com.tuber.inventory.service.domain.event.InventoryCreatedEvent;
import com.tuber.inventory.service.domain.event.WarehouseCreatedEvent;

import java.util.List;

public interface InventoryDomainService {
    WarehouseCreatedEvent validateAndInitializeWarehouse(Warehouse warehouse);

    InventoryCreatedEvent validateAndInitializeInventory(Inventory inventory);

    InventoryTransaction validateAndInitializeInventoryTransaction(InventoryTransaction inventoryTransaction);

    InventoryConfirmationEvent validateAndInitializeFulfillmentHistory(FulfillmentHistory fulfillmentHistory, InventoryOrderStatus inventoryOrderStatus, List<String> failureMessages);
}
