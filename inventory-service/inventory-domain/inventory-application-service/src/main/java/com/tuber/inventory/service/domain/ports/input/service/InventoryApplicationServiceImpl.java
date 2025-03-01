package com.tuber.inventory.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.inventory.*;
import com.tuber.inventory.service.domain.dto.warehouse.*;
import com.tuber.inventory.service.domain.helper.inventory.GoodsTransferHelper;
import com.tuber.inventory.service.domain.helper.warehouse.CreateWarehouseHelper;
import com.tuber.inventory.service.domain.helper.warehouse.ReadWarehouseHelper;
import com.tuber.inventory.service.domain.helper.warehouse.UpdateWarehouseHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryApplicationServiceImpl implements InventoryApplicationService{
    CreateWarehouseHelper createWarehouseHelper;
    ReadWarehouseHelper readWarehouseHelper;
    UpdateWarehouseHelper updateWarehouseHelper;
    GoodsTransferHelper goodsTransferHelper;

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

    @Override
    public ApiResponse<InventoriesListResponseData> importGoods(ImportGoodsCommand importGoodsCommand) {
        return goodsTransferHelper.importGoods(importGoodsCommand);
    }

    @Override
    public ApiResponse<InventoriesListResponseData> exportGoods(ExportGoodsCommand exportGoodsCommand) {
        return goodsTransferHelper.exportGoods(exportGoodsCommand);
    }

    @Override
    public ApiResponse<TransferGoodsListResponseData> transferGoods(TransferGoodsCommand transferGoodsCommand) {
        return goodsTransferHelper.transferGoods(transferGoodsCommand);
    }
}
