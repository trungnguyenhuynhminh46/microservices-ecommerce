package com.tuber.inventory.service.dataaccess.inventory.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.dataaccess.inventory.entity.InventoryJpaEntity;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.inventory.service.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class InventoryJpaMapper {
    @Mapping(target = "productId", source = "product.id")
    public abstract InventoryJpaEntity inventoryEntityToInventoryJpaEntity(Inventory inventory);

    @Mapping(target = "product", source = "productId")
    public abstract Inventory inventoryJpaEntityToInventoryEntity(InventoryJpaEntity inventoryJpaEntity);

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }
    protected UniqueUUID map(UUID id) {
        return new UniqueUUID(id);
    }

    protected Product productIdToProduct(UUID id) {
        return Product.builder().id(new UniqueUUID(id)).build();
    }
}
