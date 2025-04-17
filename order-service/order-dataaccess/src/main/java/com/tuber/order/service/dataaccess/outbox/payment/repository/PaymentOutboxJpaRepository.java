package com.tuber.order.service.dataaccess.outbox.payment.repository;

import com.tuber.order.service.dataaccess.outbox.payment.entity.PaymentOutboxMessageJpaEntity;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentOutboxJpaRepository extends JpaRepository<PaymentOutboxMessageJpaEntity, UUID> {
    Optional<List<PaymentOutboxMessageJpaEntity>> findByTypeAndOutboxStatusAndSagaStatusIn(
            String type,
            OutboxStatus outboxStatus,
            List<SagaStatus> sagaStatus
    );

    Optional<PaymentOutboxMessageJpaEntity> findByTypeAndSagaIdAndSagaStatusIn(
            String type,
            UUID sagaId,
            List<SagaStatus> sagaStatus
    );

    void deleteByTypeAndOutboxStatusAndSagaStatusIn(
            String type,
            OutboxStatus outboxStatus,
            List<SagaStatus> sagaStatus
    );
}
