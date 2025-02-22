package com.tuber.inventory.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.warehouse.CreateWarehouseCommand;
import com.tuber.inventory.service.domain.dto.warehouse.GetWarehouseQuery;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;
import com.tuber.inventory.service.domain.dto.warehouse.WarehousesListResponseData;
import com.tuber.inventory.service.domain.helper.warehouse.CreateWarehouseHelper;
import com.tuber.inventory.service.domain.helper.warehouse.ReadWarehouseHelper;

public class InventoryApplicationServiceImpl implements InventoryApplicationService{
    CreateWarehouseHelper createWarehouseHelper;
    ReadWarehouseHelper readWarehouseHelper;
    @Override
    public ApiResponse<WarehouseResponseData> createWarehouse(CreateWarehouseCommand createWarehouseCommand) {
        return createWarehouseHelper.createWarehouse(createWarehouseCommand);
    }

    @Override
    public ApiResponse<WarehouseResponseData> getSingleWarehouse(GetWarehouseQuery getWarehouseQuery) {
        return readWarehouseHelper.getSingleWarehouse(getWarehouseQuery);
    }

    @Override
    public ApiResponse<WarehousesListResponseData> getAllWarehouses() {
        return readWarehouseHelper.getAllWarehouses();
    }
}
