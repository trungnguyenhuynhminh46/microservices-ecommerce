package com.tuber.order.service.domain.ports.output.message.publisher.inventory;

import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import com.tuber.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface InventoryConfirmationRequestPublisher {
    void publish(InventoryConfirmationOutboxMessage inventoryConfirmationRequestMessage,
                  BiConsumer<InventoryConfirmationOutboxMessage, OutboxStatus> outboxCallback);
}
