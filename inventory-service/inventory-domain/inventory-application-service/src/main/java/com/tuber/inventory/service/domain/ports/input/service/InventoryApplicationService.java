package com.tuber.inventory.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.warehouse.*;
import jakarta.validation.Valid;

public interface InventoryApplicationService {
    ApiResponse<WarehouseResponseData> createWarehouse(@Valid CreateWarehouseCommand createWarehouseCommand);

    ApiResponse<WarehouseResponseData> getSingleWarehouse(@Valid GetWarehouseQuery getWarehouseQuery);

    ApiResponse<WarehousesListResponseData> getAllWarehouses();

    ApiResponse<WarehouseResponseData> updateWarehouse(@Valid UpdateWarehouseInfoCommand updateWarehouseInformationCommand);
}
