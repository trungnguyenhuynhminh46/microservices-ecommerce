package com.tuber.inventory.service.domain.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.domain.dto.inventory.InventoriesListResponseData;
import com.tuber.inventory.service.domain.dto.inventory.InventoryResponseData;
import com.tuber.inventory.service.domain.dto.inventory.TransferGoodsResponseData;
import com.tuber.inventory.service.domain.dto.inventory.internal.InternalInventoryDetailResponseData;
import com.tuber.inventory.service.domain.dto.inventory.internal.InternalInventoryDetailsResponseData;
import com.tuber.inventory.service.domain.dto.shared.AssignedAttributeDTO;
import com.tuber.inventory.service.domain.dto.shared.GoodInfoDTO;
import com.tuber.inventory.service.domain.dto.shared.ProductDTO;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.entity.Product;
import com.tuber.inventory.service.domain.valueobject.ProductAssignedAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class InventoryMapper {
    @Autowired
    ProductMapper productMapper;

    public Inventory goodInfoToEmptyInventory(GoodInfoDTO goodInfo, Product product, List<ProductAssignedAttribute> assignedAttributes, UUID warehouseId, String creator) {
        Inventory inventory = Inventory.builder()
                .product(product)
                .warehouseId(warehouseId)
                .stockQuantity(0)
                .creator(creator)
                .updater(creator)
                .build();

        inventory.setAssignedAttributes(assignedAttributes);
        return inventory;
    }

    public abstract ProductAssignedAttribute attributeDTOToProductAssignedAttribute(AssignedAttributeDTO assignedAttributeDTO);

    public abstract List<ProductAssignedAttribute> attributeDTOsToProductAssignedAttributes(List<AssignedAttributeDTO> assignedAttributeDTOS);

    @Mapping(target = "quantity", source = "stockQuantity")
    public abstract InventoryResponseData inventoryToInventoryResponseData(Inventory inventory);

    public InventoriesListResponseData inventoriesToInventoriesListResponseData(List<Inventory> inventories, List<GoodInfoDTO> failedRequests) {
        InventoriesListResponseData.InventoriesListResponseDataBuilder responseBuilder = InventoriesListResponseData.builder()
                .inventories(inventories.stream().map(this::inventoryToInventoryResponseData).collect(Collectors.toList()))
                .total(inventories.size());

        if (failedRequests != null && !failedRequests.isEmpty()) {
            responseBuilder.failedRequests(failedRequests);
        }

        return responseBuilder.build();
    }

    public TransferGoodsResponseData inventoriesToTransferGoodsResponseData(Inventory sourceInventory, Inventory destinationInventory) {
        return TransferGoodsResponseData.builder()
                .sourceInventory(inventoryToInventoryResponseData(sourceInventory))
                .destinationInventory(inventoryToInventoryResponseData(destinationInventory))
                .build();
    }

    public abstract InternalInventoryDetailResponseData inventoryToInternalInventoryDetailResponseData(Inventory inventory);

    public InternalInventoryDetailsResponseData inventoriesToInternalInventoryDetailsResponseData(Set<Inventory> inventories, boolean hasUnavailableProducts) {
        return InternalInventoryDetailsResponseData.builder()
                .inventoryDetails(inventories.stream().map(this::inventoryToInternalInventoryDetailResponseData).collect(Collectors.toSet()))
                .hasUnavailableProducts(hasUnavailableProducts)
                .build();
    }

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }

    protected ProductDTO productToProductDTO(Product product) {
        return productMapper.productToProductDTO(product);
    }
}

