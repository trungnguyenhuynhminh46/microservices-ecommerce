package com.tuber.inventory.service.dataaccess.inventory.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.dataaccess.inventory.entity.InventoryJpaEntity;
import com.tuber.inventory.service.domain.entity.Inventory;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class InventoryJpaMapper {
    public abstract InventoryJpaEntity inventoryEntityToInventoryJpaEntity(Inventory inventory);
    public abstract Inventory inventoryJpaEntityToInventoryEntity(InventoryJpaEntity inventoryJpaEntity);

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }
}
