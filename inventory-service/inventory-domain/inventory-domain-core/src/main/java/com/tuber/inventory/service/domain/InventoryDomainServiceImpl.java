package com.tuber.inventory.service.domain;

import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import com.tuber.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.event.InventoryConfirmationEvent;
import com.tuber.inventory.service.domain.event.InventoryCreatedEvent;
import com.tuber.inventory.service.domain.event.WarehouseCreatedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class InventoryDomainServiceImpl implements InventoryDomainService {
    @Override
    public WarehouseCreatedEvent validateAndInitializeWarehouse(Warehouse warehouse) {
        warehouse.validateForInitialization();
        warehouse.initialize();
        log.info("Initialize warehouse with id: {}", warehouse.getId());
        return new WarehouseCreatedEvent(warehouse);
    }

    @Override
    public InventoryCreatedEvent validateAndInitializeInventory(Inventory inventory) {
        inventory.validateForInitialization();
        inventory.initialize();
        log.info("Initialize inventory with id: {}", inventory.getId());
        return new InventoryCreatedEvent(inventory);
    }

    @Override
    public InventoryTransaction validateAndInitializeInventoryTransaction(InventoryTransaction inventoryTransaction) {
        inventoryTransaction.validateForInitialization();
        inventoryTransaction.initialize();
        log.info("Initialize inventory transaction with id: {}", inventoryTransaction.getId());
        return inventoryTransaction;
    }

    @Override
    public InventoryConfirmationEvent validateAndInitializeFulfillmentHistory(FulfillmentHistory fulfillmentHistory) {
        fulfillmentHistory.selfValidate().selfInitialize();
        return new InventoryConfirmationEvent(fulfillmentHistory, LocalDate.now());
    }
}
