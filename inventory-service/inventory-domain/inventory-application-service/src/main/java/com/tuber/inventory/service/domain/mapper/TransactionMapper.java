package com.tuber.inventory.service.domain.mapper;

import com.tuber.domain.valueobject.enums.InventoryTransactionType;
import com.tuber.inventory.service.domain.dto.inventory.TransferGoodsListResponseData;
import com.tuber.inventory.service.domain.dto.inventory.TransferGoodsResponseData;
import com.tuber.inventory.service.domain.dto.shared.GoodInfoDTO;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {
    public InventoryTransaction goodInfoToAddTransaction(GoodInfoDTO goodInfoDTO, String sku, UUID destWarehouseId, String creator) {
        return InventoryTransaction.builder()
                .productId(UUID.fromString(goodInfoDTO.getProductId()))
                .sku(sku)
                .destWarehouseId(destWarehouseId)
                .quantity(goodInfoDTO.getQuantity())
                .creator(creator)
                .transactionType(InventoryTransactionType.ADD)
                .build();
    }

    public InventoryTransaction goodInfoToRemoveTransaction(GoodInfoDTO goodInfoDTO, String sku, UUID destWarehouseId, String creator) {
        return InventoryTransaction.builder()
                .productId(UUID.fromString(goodInfoDTO.getProductId()))
                .sku(sku)
                .destWarehouseId(destWarehouseId)
                .quantity(goodInfoDTO.getQuantity())
                .creator(creator)
                .transactionType(InventoryTransactionType.REMOVE)
                .build();
    }

    public InventoryTransaction goodInfoToTransferTransaction(GoodInfoDTO goodInfoDTO, String sku, UUID srcWarehouseId, UUID destWarehouseId, String creator) {
        return InventoryTransaction.builder()
                .productId(UUID.fromString(goodInfoDTO.getProductId()))
                .sku(sku)
                .srcWarehouseId(srcWarehouseId)
                .destWarehouseId(destWarehouseId)
                .quantity(goodInfoDTO.getQuantity())
                .creator(creator)
                .transactionType(InventoryTransactionType.TRANSFER)
                .build();
    }

    public TransferGoodsListResponseData generateTransferGoodsListResponseData(List<TransferGoodsResponseData> data, List<GoodInfoDTO> failedRequests) {
        return TransferGoodsListResponseData.builder()
                .transferredGoodsInfo(data)
                .failedRequests((failedRequests == null || failedRequests.isEmpty()) ? null : failedRequests)
                .total(data.size())
                .build();
    }
}
