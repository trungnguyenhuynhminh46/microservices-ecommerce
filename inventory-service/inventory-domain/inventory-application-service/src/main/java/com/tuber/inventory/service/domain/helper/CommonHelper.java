package com.tuber.inventory.service.domain.helper;

import com.tuber.inventory.service.domain.constant.InventoryResponseCode;
import com.tuber.inventory.service.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.ports.output.repository.WarehouseRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonHelper {
    WarehouseRepository warehouseRepository;

    public Warehouse saveWarehouse(Warehouse warehouse) {
        Optional<Warehouse> savedWarehouse = warehouseRepository.save(warehouse);
        if (savedWarehouse.isEmpty()) {
            log.error(String.format("Failed to save warehouse with name %s", warehouse.getName()));
            throw new InventoryDomainException(InventoryResponseCode.WAREHOUSE_SAVED_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), warehouse.getName());
        }
        return savedWarehouse.get();
    }
}
