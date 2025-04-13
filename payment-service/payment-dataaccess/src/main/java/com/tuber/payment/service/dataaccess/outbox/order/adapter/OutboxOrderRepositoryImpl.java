package com.tuber.payment.service.dataaccess.outbox.order.adapter;

import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.dataaccess.outbox.order.mapper.OutBoxOrderMessageDataAccessMapper;
import com.tuber.payment.service.dataaccess.outbox.order.repository.OutboxOrderJpaRepository;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;
import com.tuber.payment.service.domain.ports.output.repository.OutboxOrderRepository;
import com.tuber.payment.service.domain.valueobject.enums.PaymentStatus;
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
public class OutboxOrderRepositoryImpl implements OutboxOrderRepository {
    OutboxOrderJpaRepository outboxOrderJpaRepository;
    OutBoxOrderMessageDataAccessMapper outBoxOrderMessageDataAccessMapper;

    @Override
    public OutboxOrderMessage save(OutboxOrderMessage outboxOrderMessage) {
        return outBoxOrderMessageDataAccessMapper.outboxOrderMessageJpaEntityToOutboxOrderMessage(
                outboxOrderJpaRepository.save(
                        outBoxOrderMessageDataAccessMapper.outboxOrderMessageToOutboxOrderMessageJpaEntity(outboxOrderMessage)
                )
        );
    }

    @Override
    public Optional<List<OutboxOrderMessage>> findByTypeAndOutboxStatus(String type, OutboxStatus status) {
        return outboxOrderJpaRepository.findAllByTypeAndOutboxStatus(type, status)
                .map(jpaList -> jpaList.stream().map(
                        outBoxOrderMessageDataAccessMapper::outboxOrderMessageJpaEntityToOutboxOrderMessage
                ).toList());
    }

    @Override
    public Optional<OutboxOrderMessage> findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(String type, UUID sagaId, PaymentStatus paymentStatus, OutboxStatus outboxStatus) {
        return outboxOrderJpaRepository.findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(
                type,
                sagaId,
                paymentStatus,
                outboxStatus
        ).map(outBoxOrderMessageDataAccessMapper::outboxOrderMessageJpaEntityToOutboxOrderMessage);
    }

    @Override
    public void deleteByTypeAndOutboxStatus(String type, OutboxStatus status) {
        outboxOrderJpaRepository.deleteByTypeAndOutboxStatus(type, status.toString());
    }
}
