package com.tuber.payment.service.domain.ports.output.repository;

import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;
import com.tuber.domain.valueobject.enums.PaymentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutboxOrderRepository {
    OutboxOrderMessage save(OutboxOrderMessage outboxOrderMessage);

    Optional<List<OutboxOrderMessage>> findByTypeAndOutboxStatus(String type, OutboxStatus status);

    Optional<OutboxOrderMessage> findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(String type,
                                                                                    UUID sagaId,
                                                                                    PaymentStatus paymentStatus,
                                                                                    OutboxStatus outboxStatus);
    void deleteByTypeAndOutboxStatus(String type, OutboxStatus status);
}
