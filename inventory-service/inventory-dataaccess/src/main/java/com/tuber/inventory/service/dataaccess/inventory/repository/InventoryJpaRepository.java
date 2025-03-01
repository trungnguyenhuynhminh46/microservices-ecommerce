package com.tuber.inventory.service.dataaccess.inventory.repository;

import com.tuber.inventory.service.dataaccess.inventory.entity.InventoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryJpaRepository extends JpaRepository<InventoryJpaEntity, UUID> {
    Optional<InventoryJpaEntity> findByProductIdAndSkuAndWarehouseId(UUID productId, String sku, UUID warehouseId);
}
