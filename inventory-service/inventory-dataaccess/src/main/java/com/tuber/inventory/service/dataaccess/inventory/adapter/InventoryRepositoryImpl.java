package com.tuber.inventory.service.dataaccess.inventory.adapter;

import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryRepositoryImpl implements InventoryRepository {
    @Override
    public Optional<Inventory> findByProductIdAndSkuAndWarehouseId(UUID productId, String sku, UUID warehouseId) {
        return Optional.empty();
    }

    @Override
    public Inventory save(Inventory inventory) {
        return null;
    }
}
