package com.tuber.payment.service.dataaccess.outbox.order.adapter;

import com.tuber.outbox.OutboxStatus;
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

//TODO Implement this class
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OutboxOrderRepositoryImpl implements OutboxOrderRepository {
    @Override
    public OutboxOrderMessage save(OutboxOrderMessage outboxOrderMessage) {
        return null;
    }

    @Override
    public Optional<List<OutboxOrderMessage>> findByTypeAndOutboxStatus(String type, OutboxStatus status) {
        return Optional.empty();
    }

    @Override
    public Optional<OutboxOrderMessage> findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(String type, UUID sagaId, PaymentStatus paymentStatus, OutboxStatus outboxStatus) {
        return Optional.empty();
    }

    @Override
    public void deleteByTypeAndOutboxStatus(String type, OutboxStatus status) {

    }
}
