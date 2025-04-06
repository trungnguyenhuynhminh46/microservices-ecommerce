package com.tuber.order.service.dataaccess.outbox.payment.adapter;

import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import com.tuber.order.service.domain.ports.output.repository.OutboxPaymentRepository;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//TODO: Implement this class
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OutboxPaymentRepositoryImpl implements OutboxPaymentRepository {
    @Override
    public PaymentOutboxMessage save(PaymentOutboxMessage paymentOutboxMessage) {
        return null;
    }

    @Override
    public Optional<List<PaymentOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatuses(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        return Optional.empty();
    }

    @Override
    public Optional<PaymentOutboxMessage> findBySagaIdAndTypeAndSagaStatuses(UUID sagaId, String type, SagaStatus... sagaStatuses) {
        return Optional.empty();
    }

    @Override
    public void deleteByTypeAndOutboxStatusAndSagaStatuses(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {

    }
}
