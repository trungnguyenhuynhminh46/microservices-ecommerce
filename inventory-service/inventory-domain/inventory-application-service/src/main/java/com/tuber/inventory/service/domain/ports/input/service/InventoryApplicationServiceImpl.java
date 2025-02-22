package com.tuber.inventory.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.warehouse.*;
import com.tuber.inventory.service.domain.helper.warehouse.CreateWarehouseHelper;
import com.tuber.inventory.service.domain.helper.warehouse.ReadWarehouseHelper;
import com.tuber.inventory.service.domain.helper.warehouse.UpdateWarehouseHelper;

public class InventoryApplicationServiceImpl implements InventoryApplicationService{
    CreateWarehouseHelper createWarehouseHelper;
    ReadWarehouseHelper readWarehouseHelper;
    UpdateWarehouseHelper updateWarehouseHelper;

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

    @Override
    public ApiResponse<WarehouseResponseData> updateWarehouse(UpdateWarehouseInfoCommand updateWarehouseInformationCommand) {
        return updateWarehouseHelper.updateWarehouse(updateWarehouseInformationCommand);
    }
}
