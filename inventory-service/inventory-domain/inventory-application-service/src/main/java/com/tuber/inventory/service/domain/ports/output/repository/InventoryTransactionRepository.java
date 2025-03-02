package com.tuber.inventory.service.domain.ports.output.repository;

import com.tuber.inventory.service.domain.entity.InventoryTransaction;

import java.util.List;

public interface InventoryTransactionRepository {
    InventoryTransaction save(InventoryTransaction inventoryTransaction);
    List<InventoryTransaction> findAll();
}
