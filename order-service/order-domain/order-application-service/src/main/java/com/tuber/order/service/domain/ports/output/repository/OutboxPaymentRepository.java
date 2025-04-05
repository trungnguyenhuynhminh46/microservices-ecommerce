package com.tuber.order.service.domain.ports.output.repository;

import com.tuber.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutboxPaymentRepository {
    OrderPaymentOutboxMessage save(OrderPaymentOutboxMessage orderPaymentOutboxMessage);

    Optional<List<OrderPaymentOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatuses(
            String type,
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    );

    Optional<OrderPaymentOutboxMessage> findBySagaIdAndTypeAndSagaStatuses(
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
