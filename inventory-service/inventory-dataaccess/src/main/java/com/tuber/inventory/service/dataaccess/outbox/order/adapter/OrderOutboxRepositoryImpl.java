package com.tuber.inventory.service.dataaccess.outbox.order.adapter;

import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import com.tuber.inventory.service.domain.ports.output.repository.outbox.OrderOutboxRepository;
import com.tuber.outbox.OutboxStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
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

    //TODO: Implement this method
    @Override
    public Optional<List<OrderOutboxMessage>> findByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus) {
        return Optional.empty();
    }

    //TODO: Implement this method
    @Override
    public void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus) {

    }
}
