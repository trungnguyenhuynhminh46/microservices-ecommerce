package com.tuber.inventory.service.dataaccess.inventory.adapter;

import com.tuber.inventory.service.dataaccess.inventory.mapper.InventoryJpaMapper;
import com.tuber.inventory.service.dataaccess.inventory.repository.InventoryJpaRepository;
import com.tuber.inventory.service.domain.dto.shared.ProductIdWithSkuDTO;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryRepositoryImpl implements InventoryRepository {
    InventoryJpaRepository inventoryJpaRepository;
    InventoryJpaMapper inventoryJpaMapper;

    @Override
    public Inventory save(Inventory inventory) {
        return inventoryJpaMapper.inventoryJpaEntityToInventoryEntity(
                inventoryJpaRepository.save(
                        inventoryJpaMapper.inventoryEntityToInventoryJpaEntity(inventory)
                )
        );
    }

    @Override
    public Optional<Inventory> findByProductIdAndSkuAndWarehouseId(UUID productId, String sku, UUID warehouseId) {
        return inventoryJpaRepository.findByProductIdAndSkuAndWarehouseId(productId, sku, warehouseId)
                .map(inventoryJpaMapper::inventoryJpaEntityToInventoryEntity);
    }

    @Override
    public boolean existsByProductIdsAndSkus(Set<ProductIdWithSkuDTO> productIdWithSkus) {
        return inventoryJpaRepository.existsByProductIdsAndSkus(productIdWithSkus);
    }
}
