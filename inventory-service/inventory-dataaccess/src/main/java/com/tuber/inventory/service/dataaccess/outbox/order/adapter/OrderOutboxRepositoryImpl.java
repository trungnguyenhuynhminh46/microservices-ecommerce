package com.tuber.inventory.service.dataaccess.outbox.order.adapter;

import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import com.tuber.inventory.service.domain.ports.output.repository.outbox.OrderOutboxRepository;
import com.tuber.outbox.OutboxStatus;

import java.util.Optional;
import java.util.UUID;

public class OrderOutboxRepositoryImpl implements OrderOutboxRepository {
    //TODO: Implement this method
    @Override
    public OrderOutboxMessage save(OrderOutboxMessage orderOutboxMessage) {
        return null;
    }

    //TODO: Implement this method
    @Override
    public Optional<OrderOutboxMessage> findBySagaIdAndTypeAndOutboxStatus(UUID sagaId, String type, OutboxStatus outboxStatus) {
        return Optional.empty();
    }
}
