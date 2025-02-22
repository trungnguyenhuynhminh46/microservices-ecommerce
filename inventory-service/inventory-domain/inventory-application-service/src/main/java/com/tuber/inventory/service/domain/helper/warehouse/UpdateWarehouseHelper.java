package com.tuber.inventory.service.domain.helper.warehouse;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.constant.InventoryResponseCode;
import com.tuber.inventory.service.domain.dto.warehouse.UpdateWarehouseInfoCommand;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;
import com.tuber.inventory.service.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.helper.CommonInventoryServiceHelper;
import com.tuber.inventory.service.domain.mapper.WarehouseMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateWarehouseHelper {
    WarehouseMapper warehouseMapper;
    CommonInventoryServiceHelper commonInventoryServiceHelper;

    @Transactional
    public ApiResponse<WarehouseResponseData> updateWarehouse(UpdateWarehouseInfoCommand updateWarehouseInformationCommand) {
        Warehouse updatedWarehouse = warehouseMapper
                .updateWarehouse(updateWarehouseInformationCommand,
                        commonInventoryServiceHelper.verifyWarehouseExist(
                                updateWarehouseInformationCommand.getId()
                        )
                );
        return ApiResponse.<WarehouseResponseData>builder()
                .code(InventoryResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Warehouse updated successfully")
                .data(warehouseMapper.warehouseToWarehouseResponseData(
                        commonInventoryServiceHelper.saveWarehouse(updatedWarehouse)
                ))
                .build();
    }
}
