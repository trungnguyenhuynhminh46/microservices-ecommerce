package com.tuber.order.service.dataaccess.outbox.payment.adapter;

import com.tuber.order.service.dataaccess.outbox.payment.mapper.PaymentOutboxDataAccessMapper;
import com.tuber.order.service.dataaccess.outbox.payment.repository.PaymentOutboxJpaRepository;
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

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OutboxPaymentRepositoryImpl implements OutboxPaymentRepository {
    PaymentOutboxJpaRepository paymentOutboxMessageJpaRepository;
    PaymentOutboxDataAccessMapper paymentOutboxDataAccessMapper;

    @Override
    public PaymentOutboxMessage save(PaymentOutboxMessage paymentOutboxMessage) {
        return paymentOutboxDataAccessMapper.paymentOutboxMessageJpaEntityToPaymentOutboxMessage(
                paymentOutboxMessageJpaRepository.save(
                        paymentOutboxDataAccessMapper.paymentOutboxMessageToPaymentOutboxMessageJpaEntity(paymentOutboxMessage)
                )
        );
    }

    @Override
    public Optional<List<PaymentOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatuses(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        return paymentOutboxMessageJpaRepository
                .findByTypeAndOutboxStatusAndSagaStatusIn(type, outboxStatus, List.of(sagaStatus))
                .map(paymentOutboxMessageJpaEntities -> paymentOutboxMessageJpaEntities
                        .stream()
                        .map(paymentOutboxDataAccessMapper::paymentOutboxMessageJpaEntityToPaymentOutboxMessage)
                        .toList());
    }

    @Override
    public Optional<PaymentOutboxMessage> findBySagaIdAndTypeAndSagaStatuses(UUID sagaId, String type, SagaStatus... sagaStatuses) {
        return paymentOutboxMessageJpaRepository
                .findByTypeAndSagaIdAndSagaStatusIn(type, sagaId, List.of(sagaStatuses))
                .map(paymentOutboxDataAccessMapper::paymentOutboxMessageJpaEntityToPaymentOutboxMessage);
    }

    @Override
    public void deleteByTypeAndOutboxStatusAndSagaStatuses(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        paymentOutboxMessageJpaRepository.deleteByTypeAndOutboxStatusAndSagaStatusIn(type, outboxStatus, List.of(sagaStatuses));
    }
}
