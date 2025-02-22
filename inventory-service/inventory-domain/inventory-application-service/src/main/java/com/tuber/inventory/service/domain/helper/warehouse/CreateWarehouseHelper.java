package com.tuber.inventory.service.domain.helper.warehouse;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.constant.InventoryResponseCode;
import com.tuber.inventory.service.domain.dto.warehouse.CreateWarehouseCommand;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;
import com.tuber.inventory.service.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.helper.CommonHelper;
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
    CommonHelper commonHelper;
    WarehouseMapper warehouseMapper;

    public ApiResponse<WarehouseResponseData> createWarehouse(CreateWarehouseCommand createWarehouseCommand) {
        Warehouse warehouse = warehouseMapper.createWarehouseCommandToWarehouse(createWarehouseCommand);
        Warehouse savedWarehouse = commonHelper.saveWarehouse(warehouse);
        return ApiResponse.<WarehouseResponseData>builder()
                .code(InventoryResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Warehouse created successfully")
                .data(warehouseMapper.warehouseToWarehouseResponseData(savedWarehouse))
                .build();
    }
}
