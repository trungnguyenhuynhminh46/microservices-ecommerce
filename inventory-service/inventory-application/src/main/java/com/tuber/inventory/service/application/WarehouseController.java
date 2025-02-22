package com.tuber.inventory.service.application;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.validators.ValidUUID;
import com.tuber.inventory.service.domain.dto.warehouse.CreateWarehouseCommand;
import com.tuber.inventory.service.domain.dto.warehouse.GetWarehouseQuery;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;
import com.tuber.inventory.service.domain.dto.warehouse.WarehousesListResponseData;
import com.tuber.inventory.service.domain.ports.input.service.InventoryApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/${service.name}/warehouses", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class WarehouseController {
    private final InventoryApplicationService inventoryApplicationService;
    @PostMapping
    public ResponseEntity<ApiResponse<WarehouseResponseData>> createWarehouse(@RequestBody CreateWarehouseCommand createWarehouseCommand) {
        ApiResponse<WarehouseResponseData> createWarehouseResponse = inventoryApplicationService.createWarehouse(createWarehouseCommand);
        log.info("Successfully created warehouse with name {}", createWarehouseCommand.getName());
        return ResponseEntity.ok(createWarehouseResponse);
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<ApiResponse<WarehouseResponseData>> getSingle(@PathVariable @ValidUUID String warehouseId) {
        GetWarehouseQuery getWarehouseQuery = GetWarehouseQuery.builder().id(UUID.fromString(warehouseId)).build();
        ApiResponse<WarehouseResponseData> warehouseResponse = inventoryApplicationService.getSingleWarehouse(getWarehouseQuery);
        log.info("Successfully fetched warehouse with id {}", warehouseId);
        return ResponseEntity.ok(warehouseResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<WarehousesListResponseData>> getAll() {
        ApiResponse<WarehousesListResponseData> warehousesListResponseData = inventoryApplicationService.getAllWarehouses();
        log.info("Successfully fetched all warehouses");
        return ResponseEntity.ok(warehousesListResponseData);
    }
}
