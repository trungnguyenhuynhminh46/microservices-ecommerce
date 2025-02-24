package com.tuber.inventory.service.domain.helper.inventory;

import com.tuber.application.handler.ApiResponse;
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
    public ApiResponse<InventoriesListResponseData> importGoods(ImportGoodsCommand importGoodsCommand) {
        return null;
    }

    public ApiResponse<InventoriesListResponseData> exportGoods(ExportGoodsCommand exportGoodsCommand) {
        return null;
    }
}
