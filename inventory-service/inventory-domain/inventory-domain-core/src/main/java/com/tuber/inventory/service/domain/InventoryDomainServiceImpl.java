package com.tuber.inventory.service.domain;

import com.tuber.domain.valueobject.enums.InventoryOrderStatus;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import com.tuber.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.event.*;
import com.tuber.inventory.service.domain.valueobject.enums.OrderInventoryConfirmationStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

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
    public InventoryConfirmationEvent validateAndInitializeFulfillmentHistory(FulfillmentHistory fulfillmentHistory, InventoryOrderStatus inventoryOrderStatus, List<String> failureMessages) {
        fulfillmentHistory.selfValidate(inventoryOrderStatus, failureMessages).selfInitialize(failureMessages);
        log.info("Initialize fulfillment history with id: {}", fulfillmentHistory.getId());

        OrderInventoryConfirmationStatus status = fulfillmentHistory.getOrderInventoryConfirmationStatus();
        LocalDate now = LocalDate.now();

        return switch (status) {
            case CONFIRMED -> new InventoryConfirmationConfirmedEvent(fulfillmentHistory, now);
            case FAILED -> new InventoryConfirmationRejectedEvent(fulfillmentHistory, now);
            default -> throw new IllegalStateException("Unexpected confirmation status: " + status);
        };
    }
}
