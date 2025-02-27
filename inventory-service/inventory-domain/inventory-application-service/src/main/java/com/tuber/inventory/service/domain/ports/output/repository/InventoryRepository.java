package com.tuber.inventory.service.domain.ports.output.repository;

import com.tuber.inventory.service.domain.entity.Inventory;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository {
    Optional<Inventory> findByProductIdAndSkuAndWarehouseId(UUID productId, String sku, UUID warehouseId);
    Inventory save(Inventory inventory);
}
