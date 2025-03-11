package com.tuber.inventory.service.domain.helper;

import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.domain.entity.Warehouse;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.ports.output.repository.WarehouseRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonWarehouseHelper {
    WarehouseRepository warehouseRepository;

    public Warehouse saveWarehouse(Warehouse warehouse) {
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        if (savedWarehouse == null) {
            log.error(String.format("Failed to save warehouse with name %s", warehouse.getName()));
            throw new InventoryDomainException(InventoryResponseCode.WAREHOUSE_SAVED_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), warehouse.getName());
        }
        return savedWarehouse;
    }

    public Warehouse verifyWarehouseExist(UUID warehouseId) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        if (warehouse.isEmpty()) {
            log.error(String.format("Warehouse with id %s does not exist", warehouseId));
            throw new InventoryDomainException(InventoryResponseCode.WAREHOUSE_NOT_FOUND, HttpStatus.NOT_FOUND.value(), warehouseId);
        }
        return warehouse.get();
    }
}
