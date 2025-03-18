package com.tuber.inventory.service.domain.ports.output.repository;

import com.tuber.inventory.service.domain.dto.shared.ProductIdWithSkuDTO;
import com.tuber.inventory.service.domain.entity.Inventory;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface InventoryRepository {
    Optional<Inventory> findByProductIdAndSkuAndWarehouseId(UUID productId, String sku, UUID warehouseId);
    boolean existsByProductIdsAndSkus(Set<ProductIdWithSkuDTO> productIdWithSkus);
    Inventory save(Inventory inventory);
    Set<Inventory> findAllByProductIdsAndSkusSet(Set<ProductIdWithSkuDTO> productIdWithSkus);
}
