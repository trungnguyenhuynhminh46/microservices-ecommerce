package com.tuber.inventory.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.warehouse.CreateWarehouseCommand;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;
import com.tuber.inventory.service.domain.helper.inventory.CreateWarehouseHelper;

public class InventoryApplicationServiceImpl implements InventoryApplicationService{
    CreateWarehouseHelper createWarehouseHelper;
    @Override
    public ApiResponse<WarehouseResponseData> createWarehouse(CreateWarehouseCommand createWarehouseCommand) {
        return createWarehouseHelper.createWarehouse(createWarehouseCommand);
    }
}
