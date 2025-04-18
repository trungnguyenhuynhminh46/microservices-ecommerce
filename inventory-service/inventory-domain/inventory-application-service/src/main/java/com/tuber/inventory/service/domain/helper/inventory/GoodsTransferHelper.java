package com.tuber.inventory.service.domain.helper.inventory;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.helper.CommonHelper;
import com.tuber.inventory.service.domain.InventoryDomainService;
import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.application.dto.product.ProductResponseData;
import com.tuber.inventory.service.domain.dto.inventory.*;
import com.tuber.inventory.service.domain.dto.shared.GoodInfoDTO;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import com.tuber.inventory.service.domain.entity.Product;
import com.tuber.inventory.service.domain.event.InventoryCreatedEvent;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.helper.CommonInventoryHelper;
import com.tuber.inventory.service.domain.helper.CommonInventoryTransactionHelper;
import com.tuber.inventory.service.domain.helper.CommonWarehouseHelper;
import com.tuber.inventory.service.domain.mapper.InventoryMapper;
import com.tuber.inventory.service.domain.mapper.ProductMapper;
import com.tuber.inventory.service.domain.mapper.TransactionMapper;
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
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GoodsTransferHelper {
    CommonHelper commonHelper;
    CommonWarehouseHelper commonWarehouseHelper;
    CommonInventoryTransactionHelper commonInventoryTransactionHelper;
    ProductServiceClient productServiceClient;
    InventoryDomainService inventoryDomainService;
    InventoryRepository inventoryRepository;
    CommonInventoryHelper commonInventoryHelper;
    InventoryMapper inventoryMapper;
    TransactionMapper transactionMapper;
    ProductMapper productMapper;

    protected Product verifyProductExists(String productId) {
        ResponseEntity<ApiResponse<ProductResponseData>> getProductDetailResponse = productServiceClient.getProductDetail(productId);
        return productMapper.productResponseDataToProductEntity(Objects.requireNonNull(getProductDetailResponse.getBody()).getData());
    }

    protected Inventory getExistedInventoryOrInitializedEmptyInventory(GoodInfoDTO goodInfo, UUID warehouseId, UUID creatorId) {
        Product productDetail = verifyProductExists(goodInfo.getProductId());
        Inventory emptyInventory = inventoryMapper.goodInfoToEmptyInventory(goodInfo, productDetail, inventoryMapper.attributeDTOsToProductAssignedAttributes(goodInfo.getAttributes()), warehouseId, creatorId);
        Optional<Inventory> inventory = inventoryRepository.findByProductIdAndSkuAndWarehouseId(UUID.fromString(goodInfo.getProductId()), emptyInventory.getSku(), warehouseId);
        if (inventory.isEmpty()) {
            InventoryCreatedEvent inventoryCreatedEvent = inventoryDomainService.validateAndInitializeInventory(emptyInventory);
            return inventoryCreatedEvent.getInventory();
        }

        return inventory.get();
    }

    protected Inventory addStockToInventory(GoodInfoDTO goodInfo, UUID warehouseId, UUID updaterId) {
        Inventory inventory = getExistedInventoryOrInitializedEmptyInventory(goodInfo, warehouseId, updaterId);
        inventory.addStock(goodInfo.getQuantity(), updaterId);
        return commonInventoryHelper.saveInventory(inventory);
    }

    protected Inventory removeStockFromInventory(GoodInfoDTO goodInfo, UUID warehouseId, UUID updaterId, List<GoodInfoDTO> failedToExportGoods) {
        Inventory inventory = getExistedInventoryOrInitializedEmptyInventory(goodInfo, warehouseId, updaterId);
        if (inventory.getStockQuantity() < goodInfo.getQuantity()) {
            failedToExportGoods.add(new GoodInfoDTO(goodInfo.getProductId(), goodInfo.getAttributes(), goodInfo.getQuantity() - inventory.getStockQuantity()));
            return null;
        }
        inventory.removeStock(goodInfo.getQuantity(), updaterId);

        return commonInventoryHelper.saveInventory(inventory);
    }

    protected List<GoodInfoDTO> sanitizeGoodsInfo(List<GoodInfoDTO> goodsInfo) {
        goodsInfo.removeIf(goodInfo -> goodInfo.getQuantity() <= 0);
        Map<String, GoodInfoDTO> goodsMap = goodsInfo.stream()
                .collect(Collectors.toMap(
                        goodInfo -> goodInfo.getProductId() + goodInfo.getAttributes().toString(),
                        goodInfo -> goodInfo,
                        (existing, replacement) -> {
                            existing.setQuantity(existing.getQuantity() + replacement.getQuantity());
                            return existing;
                        }
                ));
        return new ArrayList<>(goodsMap.values());
    }

    @Transactional
    public ApiResponse<InventoriesListResponseData> importGoods(ImportGoodsCommand importGoodsCommand) {
        UUID destinationWarehouseId = importGoodsCommand.getWarehouseId();
        List<Inventory> updatedInventories = new ArrayList<>();

        commonWarehouseHelper.verifyWarehouseExist(importGoodsCommand.getWarehouseId());
        UUID updaterId = commonHelper.extractUserIdFromToken();

        for (GoodInfoDTO goodInfo : sanitizeGoodsInfo(importGoodsCommand.getGoods())) {
            Inventory inventory = addStockToInventory(goodInfo, destinationWarehouseId, updaterId);
            if (inventory != null) {
                updatedInventories.add(inventory);
                InventoryTransaction transaction = inventoryDomainService.validateAndInitializeInventoryTransaction(
                        transactionMapper.goodInfoToAddTransaction(goodInfo, inventory.getSku(), destinationWarehouseId, inventory.getCreatorId(), importGoodsCommand.getDescription())
                );
                commonInventoryTransactionHelper.saveInventoryTransaction(transaction);
            }
        }

        return ApiResponse.<InventoriesListResponseData>builder()
                .code(InventoryResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Goods imported successfully")
                .data(inventoryMapper.inventoriesToInventoriesListResponseData(updatedInventories, null))
                .build();
    }

    @Transactional
    public ApiResponse<InventoriesListResponseData> exportGoods(ExportGoodsCommand exportGoodsCommand) {
        UUID destinationWarehouseId = exportGoodsCommand.getWarehouseId();
        List<Inventory> updatedInventories = new ArrayList<>();
        List<GoodInfoDTO> failedToExportGoods = new ArrayList<>();

        commonWarehouseHelper.verifyWarehouseExist(exportGoodsCommand.getWarehouseId());

        for (GoodInfoDTO goodInfo : sanitizeGoodsInfo(exportGoodsCommand.getGoods())) {
            Inventory inventory = removeStockFromInventory(goodInfo, destinationWarehouseId, commonHelper.extractUserIdFromToken(), failedToExportGoods);
            if (inventory != null) {
                updatedInventories.add(inventory);
                InventoryTransaction transaction = inventoryDomainService.validateAndInitializeInventoryTransaction(
                        transactionMapper.goodInfoToRemoveTransaction(goodInfo, inventory.getSku(), destinationWarehouseId, inventory.getCreatorId(), exportGoodsCommand.getDescription())
                );
                commonInventoryTransactionHelper.saveInventoryTransaction(transaction);
            }
        }

        if (updatedInventories.isEmpty()) {
            throw new InventoryDomainException(InventoryResponseCode.NO_GOODS_BE_EXPORTED, HttpStatus.BAD_REQUEST.value());
        }

        return ApiResponse.<InventoriesListResponseData>builder()
                .code(InventoryResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Goods exported successfully")
                .data(inventoryMapper.inventoriesToInventoriesListResponseData(updatedInventories, failedToExportGoods))
                .build();
    }

    @Transactional
    public ApiResponse<TransferGoodsListResponseData> transferGoods(TransferGoodsCommand transferGoodsCommand) {
        UUID sourceWarehouseId = UUID.fromString(transferGoodsCommand.getSourceWarehouseId());
        UUID destinationWarehouseId = UUID.fromString(transferGoodsCommand.getDestinationWarehouseId());
        List<TransferGoodsResponseData> updatedInventories = new ArrayList<>();
        List<GoodInfoDTO> failedRequests = new ArrayList<>();
        commonWarehouseHelper.verifyWarehouseExist(sourceWarehouseId);
        commonWarehouseHelper.verifyWarehouseExist(destinationWarehouseId);

        for (GoodInfoDTO goodInfoDTO : sanitizeGoodsInfo(transferGoodsCommand.getGoods())) {
            Inventory sourceInventory = removeStockFromInventory(goodInfoDTO, sourceWarehouseId, commonHelper.extractUserIdFromToken(), failedRequests);
            if (sourceInventory != null) {
                Inventory destinationInventory = addStockToInventory(goodInfoDTO, destinationWarehouseId, commonHelper.extractUserIdFromToken());
                updatedInventories.add(inventoryMapper.inventoriesToTransferGoodsResponseData(sourceInventory, destinationInventory));
                InventoryTransaction transferTransaction = inventoryDomainService.validateAndInitializeInventoryTransaction(
                        transactionMapper.goodInfoToTransferTransaction(goodInfoDTO, sourceInventory.getSku(), sourceWarehouseId, destinationWarehouseId, sourceInventory.getCreatorId(), transferGoodsCommand.getDescription())
                );
                commonInventoryTransactionHelper.saveInventoryTransaction(transferTransaction);
            }
        }

        if (updatedInventories.isEmpty()) {
            throw new InventoryDomainException(InventoryResponseCode.NO_GOODS_BE_TRANSFERRED, HttpStatus.BAD_REQUEST.value());
        }

        return ApiResponse.<TransferGoodsListResponseData>builder()
                .code(InventoryResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Goods transferred successfully")
                .data(transactionMapper.generateTransferGoodsListResponseData(updatedInventories, failedRequests))
                .build();
    }
}
