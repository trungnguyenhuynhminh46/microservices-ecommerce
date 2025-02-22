package com.tuber.inventory.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.warehouse.CreateWarehouseCommand;
import com.tuber.inventory.service.domain.dto.warehouse.GetWarehouseQuery;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;
import com.tuber.inventory.service.domain.dto.warehouse.WarehousesListResponseData;
import jakarta.validation.Valid;

public interface InventoryApplicationService {
    ApiResponse<WarehouseResponseData> createWarehouse(@Valid CreateWarehouseCommand createWarehouseCommand);

    ApiResponse<WarehouseResponseData> getSingleWarehouse(GetWarehouseQuery getWarehouseQuery);

    ApiResponse<WarehousesListResponseData> getAllWarehouses();
}
