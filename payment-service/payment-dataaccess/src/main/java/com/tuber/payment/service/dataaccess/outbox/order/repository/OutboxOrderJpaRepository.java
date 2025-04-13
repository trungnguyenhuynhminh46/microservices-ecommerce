package com.tuber.payment.service.dataaccess.outbox.order.repository;

import com.tuber.payment.service.dataaccess.outbox.order.entity.OutboxOrderMessageJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OutboxOrderJpaRepository extends JpaRepository<OutboxOrderMessageJpaEntity, UUID> {
    Optional<List<OutboxOrderMessageJpaEntity>> findAllByTypeAndOutboxStatus(String type, String outboxStatus);
    Optional<OutboxOrderMessageJpaEntity> findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(
            String type,
            UUID sagaId,
            String paymentStatus,
            String outboxStatus
    );
    void deleteByTypeAndOutboxStatus(String type, String outboxStatus);
}
