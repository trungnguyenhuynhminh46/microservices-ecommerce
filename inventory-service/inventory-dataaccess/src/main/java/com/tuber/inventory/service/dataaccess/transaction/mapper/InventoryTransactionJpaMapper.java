package com.tuber.inventory.service.dataaccess.transaction.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.dataaccess.transaction.entity.InventoryTransactionJpaEntity;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class InventoryTransactionJpaMapper {
    public abstract InventoryTransaction inventoryTransactionJpaEntityToInventoryTransactionEntity(InventoryTransactionJpaEntity inventory);
    public abstract InventoryTransactionJpaEntity inventoryTransactionEntityToInventoryTransactionJpaEntity(InventoryTransaction inventoryJpaEntity);

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }

    protected UniqueUUID map(UUID id) {
        return new UniqueUUID(id);
    }
}
