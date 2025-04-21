package com.tuber.inventory.service.domain.ports.output.repository.outbox;

import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import com.tuber.outbox.OutboxStatus;

import java.util.Optional;
import java.util.UUID;

public interface OrderOutboxRepository {
    OrderOutboxMessage save(OrderOutboxMessage orderOutboxMessage);
    Optional<OrderOutboxMessage> findBySagaIdAndTypeAndOutboxStatus(
            UUID sagaId,
            String type,
            OutboxStatus outboxStatus
    );
}
