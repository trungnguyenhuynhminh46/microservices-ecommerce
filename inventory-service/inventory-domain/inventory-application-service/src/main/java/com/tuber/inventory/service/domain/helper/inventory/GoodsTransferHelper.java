package com.tuber.inventory.service.domain.helper.inventory;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.helper.CommonHelper;
import com.tuber.application.helper.CommonProductHelper;
import com.tuber.inventory.service.domain.InventoryDomainService;
import com.tuber.inventory.service.domain.constant.InventoryResponseCode;
import com.tuber.inventory.service.domain.dto.http.client.product.ProductAttributeDTO;
import com.tuber.inventory.service.domain.dto.http.client.product.ProductResponseData;
import com.tuber.inventory.service.domain.dto.inventory.ExportGoodsCommand;
import com.tuber.inventory.service.domain.dto.inventory.ImportGoodsCommand;
import com.tuber.inventory.service.domain.dto.inventory.InventoriesListResponseData;
import com.tuber.inventory.service.domain.dto.shared.AttributeDTO;
import com.tuber.inventory.service.domain.dto.shared.GoodInfoDTO;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.event.InventoryCreatedEvent;
import com.tuber.inventory.service.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.helper.CommonInventoryHelper;
import com.tuber.inventory.service.domain.helper.CommonWarehouseHelper;
import com.tuber.inventory.service.domain.mapper.InventoryMapper;
import com.tuber.inventory.service.domain.ports.output.http.client.ProductServiceClient;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GoodsTransferHelper {
    CommonHelper commonHelper;
    CommonProductHelper commonProductHelper;
    CommonWarehouseHelper commonWarehouseHelper;
    ProductServiceClient productServiceClient;
    InventoryDomainService inventoryDomainService;
    InventoryRepository inventoryRepository;
    CommonInventoryHelper commonInventoryHelper;
    InventoryMapper inventoryMapper;

    protected ProductResponseData verifyProductExists(String productId) {
        ResponseEntity<ApiResponse<ProductResponseData>> getProductDetailResponse = productServiceClient.getProductDetail(productId);
        return Objects.requireNonNull(getProductDetailResponse.getBody()).getData();
    }

    protected List<AttributeDTO> validateAttributes(ProductResponseData productDetail, List<AttributeDTO> attributes) {
        List<ProductAttributeDTO> productAttributes = productDetail.getAttributes();
        for (AttributeDTO attribute : attributes) {
            if (productAttributes.stream().noneMatch(productAttribute -> productAttribute.getName().equals(attribute.getName()))) {
                throw new InventoryDomainException(InventoryResponseCode.PRODUCT_ATTRIBUTE_NOT_EXISTS, HttpStatus.BAD_REQUEST.value(), productDetail.getName(), attribute.getName());
            }
        }
        return attributes;
    }

    protected Inventory createInventoryByGoodInfo(GoodInfoDTO goodInfo, String sku, UUID warehouseId, String creator) {
        Inventory inventory = inventoryMapper.goodInfoToInventory(goodInfo, sku, warehouseId, creator);
        InventoryCreatedEvent inventoryCreatedEvent = inventoryDomainService.validateAndInitializeInventory(inventory);

        return commonInventoryHelper.saveInventory(inventoryCreatedEvent.getInventory());
    }

    protected Inventory addStockToInventory(GoodInfoDTO goodInfo, UUID warehouseId, String updater) {
        ProductResponseData productDetail = verifyProductExists(goodInfo.getProductId());
        String sku = commonProductHelper.encodeAttributesToSku(inventoryMapper.attributeDTOsListToMapStringString(validateAttributes(productDetail, goodInfo.getAttributes())));
        Optional<Inventory> inventory = inventoryRepository.findByProductIdAndSkuAndWarehouseId(UUID.fromString(goodInfo.getProductId()), sku, warehouseId);
        if (inventory.isEmpty()) {
            return createInventoryByGoodInfo(goodInfo, sku, warehouseId, updater);
        }

        inventory.get().addStock(goodInfo.getQuantity());

        return commonInventoryHelper.saveInventory(inventory.get());
    }

    protected Inventory removeStockFromInventory(GoodInfoDTO goodInfo, UUID warehouseId, String updater) {
        ProductResponseData productDetail = verifyProductExists(goodInfo.getProductId());
        String sku = commonProductHelper.encodeAttributesToSku(inventoryMapper.attributeDTOsListToMapStringString(validateAttributes(productDetail, goodInfo.getAttributes())));
        Optional<Inventory> inventory = inventoryRepository.findByProductIdAndSkuAndWarehouseId(UUID.fromString(goodInfo.getProductId()), sku, warehouseId);
        if (inventory.isEmpty()) {
            // Throw error: Can not remove stock from non-existing inventory
        }

        if (inventory.get().getStockQuantity() < goodInfo.getQuantity()) {
            // Throw error: There is not enough stock to remove

        }

        inventory.get().removeStock(goodInfo.getQuantity());

        return commonInventoryHelper.saveInventory(inventory.get());
    }

    @Transactional
    public ApiResponse<InventoriesListResponseData> importGoods(ImportGoodsCommand importGoodsCommand) {
        commonWarehouseHelper.verifyWarehouseExist(importGoodsCommand.getWarehouseId());
        String updaterUsername = commonHelper.extractTokenSubject();
        List<Inventory> updatedInventories = new ArrayList<>();

        for (GoodInfoDTO goodInfo : importGoodsCommand.getGoods()) {
            updatedInventories.add(addStockToInventory(goodInfo, importGoodsCommand.getWarehouseId(), updaterUsername));
        }

        // Initialize transaction
        // Save transaction
        // Return response
        return ApiResponse.<InventoriesListResponseData>builder()
                .code(InventoryResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Goods imported successfully")
                .data(inventoryMapper.inventoriesToInventoriesListResponseData(updatedInventories))
                .build();
    }

    public ApiResponse<InventoriesListResponseData> exportGoods(ExportGoodsCommand exportGoodsCommand) {
        commonWarehouseHelper.verifyWarehouseExist(exportGoodsCommand.getWarehouseId());
        String updaterUsername = commonHelper.extractTokenSubject();
        List<Inventory> updatedInventories = new ArrayList<>();

        for (GoodInfoDTO goodInfo : exportGoodsCommand.getGoods()) {
            updatedInventories.add(removeStockFromInventory(goodInfo, exportGoodsCommand.getWarehouseId(), updaterUsername));
        }

        // Initialize transaction
        // Save transaction
        // Return response
        return ApiResponse.<InventoriesListResponseData>builder()
                .code(InventoryResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Goods exported successfully")
                .data(inventoryMapper.inventoriesToInventoriesListResponseData(updatedInventories))
                .build();
    }
}
