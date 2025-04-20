package com.tuber.order.service.dataaccess.outbox.inventory.mapper;

import com.tuber.order.service.dataaccess.outbox.inventory.entity.InventoryConfirmationOutboxMessageJpaEntity;
import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class InventoryConfirmationOutboxDataAccessMapper {
    public abstract InventoryConfirmationOutboxMessage inventoryConfirmationOutboxMessageJpaEntityToInventoryConfirmationOutboxMessage(
            InventoryConfirmationOutboxMessageJpaEntity inventoryConfirmationOutboxMessageJpaEntity);

    public abstract InventoryConfirmationOutboxMessageJpaEntity inventoryConfirmationOutboxMessageToInventoryConfirmationOutboxMessageJpaEntity(
            InventoryConfirmationOutboxMessage inventoryConfirmationOutboxMessage);
}
