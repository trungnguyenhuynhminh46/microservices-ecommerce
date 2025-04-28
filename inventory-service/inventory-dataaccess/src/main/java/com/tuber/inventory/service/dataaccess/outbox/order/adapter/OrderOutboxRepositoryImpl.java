package com.tuber.inventory.service.dataaccess.outbox.order.adapter;

import com.tuber.inventory.service.dataaccess.outbox.order.mapper.OrderOutboxMessageJpaMapper;
import com.tuber.inventory.service.dataaccess.outbox.order.repository.OrderOutboxMessageJpaRepository;
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
    OrderOutboxMessageJpaRepository orderOutboxMessageJpaRepository;
    OrderOutboxMessageJpaMapper orderOutboxMessageJpaMapper;

    @Override
    public OrderOutboxMessage save(OrderOutboxMessage orderOutboxMessage) {
        return orderOutboxMessageJpaMapper.orderOutboxMessageJpaEntityToOrderOutboxMessage(
                orderOutboxMessageJpaRepository.save(
                        orderOutboxMessageJpaMapper.orderOutboxMessageToOrderOutboxMessageJpaEntity(
                                orderOutboxMessage
                        )
                )
        );
    }

    @Override
    public Optional<OrderOutboxMessage> findBySagaIdAndTypeAndOutboxStatus(UUID sagaId, String type, OutboxStatus outboxStatus) {
        return orderOutboxMessageJpaRepository
                .findBySagaIdAndTypeAndOutboxStatus(sagaId, type, outboxStatus)
                .map(orderOutboxMessageJpaMapper::orderOutboxMessageJpaEntityToOrderOutboxMessage);
    }

    @Override
    public Optional<List<OrderOutboxMessage>> findByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus) {
        return orderOutboxMessageJpaRepository
                .findByTypeAndOutboxStatus(type, outboxStatus)
                .map(orderOutboxMessageJpaEntities -> orderOutboxMessageJpaEntities
                        .stream()
                        .map(orderOutboxMessageJpaMapper::orderOutboxMessageJpaEntityToOrderOutboxMessage)
                        .toList());
    }

    @Override
    public void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus) {
        orderOutboxMessageJpaRepository.deleteByTypeAndOutboxStatus(type, outboxStatus);
    }
}
