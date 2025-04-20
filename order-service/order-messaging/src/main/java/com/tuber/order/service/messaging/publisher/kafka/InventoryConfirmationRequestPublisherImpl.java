package com.tuber.order.service.messaging.publisher.kafka;

import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import com.tuber.order.service.domain.ports.output.message.publisher.inventory.InventoryConfirmationRequestPublisher;
import com.tuber.outbox.OutboxStatus;

import java.util.function.BiConsumer;

//TODO: Implement this class
public class InventoryConfirmationRequestPublisherImpl implements InventoryConfirmationRequestPublisher {
    @Override
    public void publish(InventoryConfirmationOutboxMessage inventoryConfirmationRequestMessage, BiConsumer<InventoryConfirmationOutboxMessage, OutboxStatus> outboxCallback) {

    }
}
