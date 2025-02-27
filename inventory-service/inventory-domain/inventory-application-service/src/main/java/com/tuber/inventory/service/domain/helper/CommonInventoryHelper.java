package com.tuber.inventory.service.domain.helper;

import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonInventoryHelper {
    InventoryRepository inventoryRepository;

    public Inventory saveInventory(Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        if (savedInventory == null) {
            log.error(String.format("Failed to save inventory with product id %s", inventory.getProductId()));
            throw new RuntimeException("Failed to save inventory");
        }
        return savedInventory;
    }
}
