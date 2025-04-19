package com.tuber.order.service.domain.ports.output.repository.outbox;

import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;

public interface InventoryConfirmationOutboxRepository {

    InventoryConfirmationOutboxMessage save(InventoryConfirmationOutboxMessage inventoryConfirmationOutboxMessage);
}
