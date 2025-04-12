package com.tuber.order.service.domain.outbox.scheduler.payment;

import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import com.tuber.order.service.domain.ports.output.message.publisher.payment.PaymentMessageRequestPublisher;
import com.tuber.order.service.domain.ports.output.repository.OutboxPaymentRepository;
import com.tuber.outbox.AbstractOutboxDispatcher;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;
import com.tuber.saga.order.SagaName;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OutboxPaymentDispatcher extends AbstractOutboxDispatcher<PaymentOutboxMessage> {
    OutboxPaymentRepository outboxPaymentRepository;
    PaymentMessageRequestPublisher paymentMessageRequestPublisher;

    @Override
    protected Optional<List<PaymentOutboxMessage>> findOutboxMessages() {
        return outboxPaymentRepository.findByTypeAndOutboxStatusAndSagaStatuses(
                SagaName.ORDER_PROCESSING_SAGA.name(),
                OutboxStatus.STARTED,
                SagaStatus.STARTED,
                SagaStatus.COMPENSATING
        );
    }

    @Override
    protected void publishMessage(PaymentOutboxMessage message, BiConsumer<PaymentOutboxMessage, OutboxStatus> outboxCallback) {
        paymentMessageRequestPublisher.publish(message, this::updateOutboxStatus);
    }

    @Override
    protected String getMessageId(PaymentOutboxMessage message) {
        return message.getId().toString();
    }

    @Override
    protected void updateOutboxStatus(PaymentOutboxMessage message, OutboxStatus outboxStatus) {
        message.setOutboxStatus(outboxStatus);
        outboxPaymentRepository.save(message);
        log.info("Updated OrderPaymentOutboxMessage with new outbox status: {}", outboxStatus.name());
    }

    @Scheduled(fixedDelayString = "${config-data.fixed-delay-string}",
            initialDelayString = "${config-data.init-delay-string}")
    public void scheduleDispatchOutboxMessages() {
        dispatchOutboxMessage();
    }
}
