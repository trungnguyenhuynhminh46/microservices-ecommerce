package com.tuber.inventory.service.domain.helper.inventory;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.helper.CommonHelper;
import com.tuber.inventory.service.domain.dto.inventory.ExportGoodsCommand;
import com.tuber.inventory.service.domain.dto.inventory.ImportGoodsCommand;
import com.tuber.inventory.service.domain.dto.inventory.InventoriesListResponseData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GoodsTransferHelper {
    CommonHelper commonHelper;
    public ApiResponse<InventoriesListResponseData> importGoods(ImportGoodsCommand importGoodsCommand) {
        // Verify product with productId exists
        // Verify warehouse with warehouseId exists
        // Get creator's username
        String creator = commonHelper.extractTokenSubject();
        // Initialize / Update inventory
        // Initialize transaction
        // Save inventory
        // Save transaction
        // Return response
        return null;
    }

    public ApiResponse<InventoriesListResponseData> exportGoods(ExportGoodsCommand exportGoodsCommand) {
        // Verify product with productId exists
        // Verify warehouse with warehouseId exists
        // Get creator's username
        String creator = commonHelper.extractTokenSubject();
        // Initialize / Update inventory
        // Initialize transaction
        // Save inventory
        // Save transaction
        // Return response
        return null;
    }
}
