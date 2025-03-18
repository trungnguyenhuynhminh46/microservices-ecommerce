package com.tuber.inventory.service.dataaccess.inventory.repository;

import com.tuber.inventory.service.dataaccess.inventory.entity.InventoryJpaEntity;
import com.tuber.inventory.service.domain.dto.shared.ProductIdWithSkuDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface InventoryJpaRepository extends JpaRepository<InventoryJpaEntity, UUID> {
    Optional<InventoryJpaEntity> findByProductIdAndSkuAndWarehouseId(UUID productId, String sku, UUID warehouseId);

    //TODO: Confirm this can be ran correctly. If not, generate product and sku tuples
    @Query("""
            SELECT COUNT(1) > 0
            FROM InventoryJpaEntity i
            WHERE (i.productId, i.sku) IN :productIdWithSkus
            """)
    boolean existsByProductIdsAndSkus(@Param("productIdWithSkus") Set<ProductIdWithSkuDTO> productIdWithSkus);


    //TODO: Confirm this can be ran correctly. If not, generate product and sku tuples
    @Query("""
            SELECT i FROM InventoryJpaEntity i
            WHERE (i.productId, i.sku) IN :productIdWithSkus
            """)
    Set<InventoryJpaEntity> findAllByProductIdsAndSkusSet(@Param("productIdWithSkus") Set<ProductIdWithSkuDTO> productIdWithSkus);
}
