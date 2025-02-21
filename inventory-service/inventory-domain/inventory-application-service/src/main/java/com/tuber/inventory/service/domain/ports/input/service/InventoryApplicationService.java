package com.tuber.inventory.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.warehouse.CreateWarehouseCommand;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;
import jakarta.validation.Valid;

public interface InventoryApplicationService {
    ApiResponse<WarehouseResponseData> createWarehouse(@Valid CreateWarehouseCommand createWarehouseCommand);
}
