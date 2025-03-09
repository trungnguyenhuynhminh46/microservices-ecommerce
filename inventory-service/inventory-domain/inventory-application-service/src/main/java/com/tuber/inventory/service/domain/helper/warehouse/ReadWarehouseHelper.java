package com.tuber.inventory.service.domain.helper.warehouse;

import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.constant.InventoryResponseCode;
import com.tuber.inventory.service.domain.dto.warehouse.GetWarehouseQuery;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;
import com.tuber.inventory.service.domain.dto.warehouse.WarehousesListResponseData;
import com.tuber.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.helper.CommonWarehouseHelper;
import com.tuber.inventory.service.domain.mapper.WarehouseMapper;
import com.tuber.inventory.service.domain.ports.output.repository.WarehouseRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReadWarehouseHelper {
    WarehouseMapper warehouseMapper;
    CommonWarehouseHelper commonWarehouseHelper;
    WarehouseRepository warehouseRepository;

    public ApiResponse<WarehouseResponseData> getSingleWarehouse(GetWarehouseQuery getWarehouseQuery) {
        Warehouse warehouse = commonWarehouseHelper.verifyWarehouseExist(getWarehouseQuery.getId());
        return ApiResponse.<WarehouseResponseData>builder()
                .code(InventoryResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Warehouse retrieved successfully")
                .data(warehouseMapper.warehouseToWarehouseResponseData(warehouse))
                .build();
    }

    public ApiResponse<WarehousesListResponseData> getAllWarehouses() {
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        return ApiResponse.<WarehousesListResponseData>builder()
                .code(InventoryResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Warehouses retrieved successfully")
                .data(warehouseMapper.warehousesListToWarehousesListResponseData(warehouseList))
                .build();
    }
}
