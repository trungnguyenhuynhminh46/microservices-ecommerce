package com.tuber.inventory.service.domain.ports.output.message.publisher;

import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import com.tuber.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface InventoryConfirmationResponsePublisher {
    void publish(OrderOutboxMessage orderOutboxMessage,
                 BiConsumer<OrderOutboxMessage, OutboxStatus> onSuccessOutbox);
}
