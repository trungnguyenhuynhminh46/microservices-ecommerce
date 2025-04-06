package com.tuber.order.service.domain.ports.output.repository;

import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutboxPaymentRepository {
    PaymentOutboxMessage save(PaymentOutboxMessage paymentOutboxMessage);

    Optional<List<PaymentOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatuses(
            String type,
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    );

    Optional<PaymentOutboxMessage> findBySagaIdAndTypeAndSagaStatuses(
            UUID sagaId,
            String type,
            SagaStatus... sagaStatuses
    );

    void deleteByTypeAndOutboxStatusAndSagaStatuses(
            String type,
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatuses
    );
}
