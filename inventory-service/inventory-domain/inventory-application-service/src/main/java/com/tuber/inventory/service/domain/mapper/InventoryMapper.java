package com.tuber.inventory.service.domain.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.domain.dto.inventory.InventoriesListResponseData;
import com.tuber.inventory.service.domain.dto.inventory.InventoryResponseData;
import com.tuber.inventory.service.domain.dto.shared.AttributeDTO;
import com.tuber.inventory.service.domain.dto.shared.GoodInfoDTO;
import com.tuber.inventory.service.domain.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class InventoryMapper {
    public Inventory goodInfoToEmptyInventory(GoodInfoDTO goodInfo, String sku, UUID warehouseId, String creator) {
        return Inventory.builder()
                .productId(UUID.fromString(goodInfo.getProductId()))
                .sku(sku)
                .warehouseId(warehouseId)
                .stockQuantity(0)
                .build();
    }

    public Map<String, String> attributeDTOsListToMapStringString(List<AttributeDTO> attributeDTOs) {
        return attributeDTOs.stream().collect(Collectors.toMap(AttributeDTO::getName, AttributeDTO::getValue));
    }

    @Mapping(target = "quantity", source = "stockQuantity")
    public abstract InventoryResponseData inventoryToInventoryResponseData(Inventory inventory);

    public InventoriesListResponseData inventoriesToInventoriesListResponseData(List<Inventory> inventories) {
        return InventoriesListResponseData.builder()
                .inventories(inventories.stream().map(this::inventoryToInventoryResponseData).collect(Collectors.toList()))
                .total(inventories.size())
                .build();
    }

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }
}

