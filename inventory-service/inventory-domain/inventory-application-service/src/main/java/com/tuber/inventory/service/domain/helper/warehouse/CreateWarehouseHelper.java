package com.tuber.inventory.service.domain.helper.warehouse;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.InventoryDomainService;
import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.inventory.service.domain.dto.warehouse.CreateWarehouseCommand;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;
import com.tuber.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.event.WarehouseCreatedEvent;
import com.tuber.inventory.service.domain.helper.CommonWarehouseHelper;
import com.tuber.inventory.service.domain.mapper.WarehouseMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateWarehouseHelper {
    CommonWarehouseHelper commonWarehouseHelper;
    WarehouseMapper warehouseMapper;
    InventoryDomainService inventoryDomainService;

    public ApiResponse<WarehouseResponseData> createWarehouse(CreateWarehouseCommand createWarehouseCommand) {
        Warehouse warehouse = warehouseMapper.createWarehouseCommandToWarehouse(createWarehouseCommand);
        WarehouseCreatedEvent warehouseCreatedEvent = inventoryDomainService.validateAndInitializeWarehouse(warehouse);

        Warehouse initiailizedWarehouse = warehouseCreatedEvent.getWarehouse();
        WarehouseResponseData warehouseResponseData =
                warehouseMapper.warehouseToWarehouseResponseData(
                        commonWarehouseHelper.saveWarehouse(initiailizedWarehouse)
                );

        return ApiResponse.<WarehouseResponseData>builder()
                .code(InventoryResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Warehouse created successfully")
                .data(warehouseResponseData)
                .build();
    }
}
