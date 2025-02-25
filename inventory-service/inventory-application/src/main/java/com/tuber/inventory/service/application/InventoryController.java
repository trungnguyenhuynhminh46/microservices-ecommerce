package com.tuber.inventory.service.application;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.validators.ValidUUID;
import com.tuber.inventory.service.domain.dto.inventory.ExportGoodsCommand;
import com.tuber.inventory.service.domain.dto.inventory.ImportGoodsCommand;
import com.tuber.inventory.service.domain.dto.inventory.InventoriesListResponseData;
import com.tuber.inventory.service.domain.ports.input.service.InventoryApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/${service.name}/inventory", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryApplicationService inventoryApplicationService;

    @PostMapping("/import/{warehouseId}")
    @PreAuthorize("hasAuthority('IMPORT_GOODS')")
    public ResponseEntity<ApiResponse<InventoriesListResponseData>> importGoods(@PathVariable("warehouseId") @ValidUUID String warehouseId, @RequestBody ImportGoodsCommand importGoodsCommand) {
        importGoodsCommand.setWarehouseId(UUID.fromString(warehouseId));
        ApiResponse<InventoriesListResponseData> importGoodsResponse = inventoryApplicationService.importGoods(importGoodsCommand);
        log.info("Successfully imported {} products", importGoodsCommand.getGoods().size());
        return ResponseEntity.ok(importGoodsResponse);
    }

    @PostMapping("/export/{warehouseId}")
    @PreAuthorize("hasAuthority('EXPORT_GOODS')")
    public ResponseEntity<ApiResponse<InventoriesListResponseData>> exportGoods(@PathVariable("warehouseId") @ValidUUID String warehouseId, @RequestBody ExportGoodsCommand exportGoodsCommand) {
        exportGoodsCommand.setWarehouseId(UUID.fromString(warehouseId));
        ApiResponse<InventoriesListResponseData> exportGoodsResponse = inventoryApplicationService.exportGoods(exportGoodsCommand);
        log.info("Successfully exported {} products", exportGoodsCommand.getGoods().size());
        return ResponseEntity.ok(exportGoodsResponse);
    }
}
