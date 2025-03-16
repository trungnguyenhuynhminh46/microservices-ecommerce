package com.tuber.inventory.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.inventory.*;
import com.tuber.inventory.service.domain.dto.inventory.internal.InternalInventoryDetailsResponseData;
import com.tuber.inventory.service.domain.dto.transaction.TransactionsListResponseData;
import com.tuber.inventory.service.domain.dto.warehouse.*;
import jakarta.validation.Valid;

public interface InventoryApplicationService {
    ApiResponse<WarehouseResponseData> createWarehouse(@Valid CreateWarehouseCommand createWarehouseCommand);

    ApiResponse<WarehouseResponseData> getSingleWarehouse(@Valid GetWarehouseQuery getWarehouseQuery);

    ApiResponse<WarehousesListResponseData> getAllWarehouses();

    ApiResponse<WarehouseResponseData> updateWarehouse(@Valid UpdateWarehouseInfoCommand updateWarehouseInformationCommand);

    ApiResponse<InventoriesListResponseData> importGoods(@Valid ImportGoodsCommand importGoodsCommand);

    ApiResponse<InventoriesListResponseData> exportGoods(@Valid ExportGoodsCommand exportGoodsCommand);

    ApiResponse<TransferGoodsListResponseData> transferGoods(@Valid TransferGoodsCommand transferGoodsCommand);

    ApiResponse<TransactionsListResponseData> getAllTransactions();

    ApiResponse<InternalInventoryDetailsResponseData> getInventoryDetails();
}
