package com.tuber.inventory.service.domain.ports.output.repository.outbox;

import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import com.tuber.outbox.OutboxStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderOutboxRepository {
    OrderOutboxMessage save(OrderOutboxMessage orderOutboxMessage);
    Optional<OrderOutboxMessage> findBySagaIdAndTypeAndOutboxStatus(
            UUID sagaId,
            String type,
            OutboxStatus outboxStatus
    );
    Optional<List<OrderOutboxMessage>> findByTypeAndOutboxStatus(
            String type,
            OutboxStatus outboxStatus
    );
    void deleteByTypeAndOutboxStatus(
            String type,
            OutboxStatus outboxStatus
    );
}
