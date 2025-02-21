package com.tuber.inventory.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.warehouse.CreateWarehouseCommand;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;

public class InventoryApplicationServiceImpl implements InventoryApplicationService{
    @Override
    public ApiResponse<WarehouseResponseData> createWarehouse(CreateWarehouseCommand createWarehouseCommand) {
        return null;
    }
}
