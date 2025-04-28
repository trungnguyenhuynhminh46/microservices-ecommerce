package com.tuber.inventory.service.dataaccess.outbox.order.repository;

import com.tuber.inventory.service.dataaccess.outbox.order.entity.OrderOutboxMessageJpaEntity;
import com.tuber.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderOutboxMessageJpaRepository extends JpaRepository<OrderOutboxMessageJpaEntity, UUID> {
    Optional<OrderOutboxMessageJpaEntity> findBySagaIdAndTypeAndOutboxStatus(UUID sagaId, String type, OutboxStatus outboxStatus);
    Optional<List<OrderOutboxMessageJpaEntity>> findByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);
    void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);
}
