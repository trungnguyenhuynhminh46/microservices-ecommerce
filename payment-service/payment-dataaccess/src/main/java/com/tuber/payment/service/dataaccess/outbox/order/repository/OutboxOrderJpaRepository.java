package com.tuber.payment.service.dataaccess.outbox.order.repository;

import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.dataaccess.outbox.order.entity.OutboxOrderMessageJpaEntity;
import com.tuber.payment.service.domain.valueobject.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OutboxOrderJpaRepository extends JpaRepository<OutboxOrderMessageJpaEntity, UUID> {
    Optional<List<OutboxOrderMessageJpaEntity>> findAllByTypeAndOutboxStatus(String type, OutboxStatus status);
    Optional<OutboxOrderMessageJpaEntity> findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(
            String type,
            UUID sagaId,
            PaymentStatus paymentStatus,
            OutboxStatus status
    );
    void deleteByTypeAndOutboxStatus(String type, String outboxStatus);
}
