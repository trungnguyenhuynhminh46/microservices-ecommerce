package com.tuber.inventory.service.domain.ports.output.repository;

import com.tuber.inventory.service.domain.entity.InventoryTransaction;

public interface InventoryTransactionRepository {
    InventoryTransaction save(InventoryTransaction inventoryTransaction);
}
