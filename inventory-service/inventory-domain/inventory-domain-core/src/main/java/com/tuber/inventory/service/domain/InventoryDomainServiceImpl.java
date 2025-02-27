package com.tuber.inventory.service.domain;

import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.event.InventoryCreatedEvent;
import com.tuber.inventory.service.domain.event.WarehouseCreatedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryDomainServiceImpl implements InventoryDomainService {
    @Override
    public WarehouseCreatedEvent validateAndInitializeWarehouse(Warehouse warehouse) {
        warehouse.validateForInitialization();
        warehouse.initialize();
        log.info("Validating warehouse with id: {}", warehouse.getId());
        return new WarehouseCreatedEvent(warehouse);
    }

    @Override
    public InventoryCreatedEvent validateAndInitializeInventory(Inventory inventory) {
        inventory.validateForInitialization();
        inventory.initialize();
        log.info("Validating inventory with id: {}", inventory.getId());
        return new InventoryCreatedEvent(inventory);
    }
}
