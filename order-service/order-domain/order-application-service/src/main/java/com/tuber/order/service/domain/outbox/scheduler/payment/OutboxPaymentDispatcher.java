package com.tuber.order.service.domain.outbox.scheduler.payment;

import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import com.tuber.order.service.domain.ports.output.message.publisher.payment.PaymentMessageRequestPublisher;
import com.tuber.order.service.domain.ports.output.repository.OutboxPaymentRepository;
import com.tuber.outbox.OutboxSchedulerDispatcher;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;
import com.tuber.saga.order.SagaName;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OutboxPaymentDispatcher implements OutboxSchedulerDispatcher {
    OutboxPaymentRepository outboxPaymentRepository;
    PaymentMessageRequestPublisher paymentMessageRequestPublisher;

    private void updateOutboxStatus(PaymentOutboxMessage paymentOutboxMessage, OutboxStatus outboxStatus) {
        paymentOutboxMessage.setOutboxStatus(outboxStatus);
        outboxPaymentRepository.save(paymentOutboxMessage);
        log.info("Updated OrderPaymentOutboxMessage with new outbox status: {}", outboxStatus.name());
    }

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${config-data.fixed-delay-string}",
            initialDelayString = "${config-data.init-delay-string}")
    public void dispatchOutboxMessage() {
        Optional<List<PaymentOutboxMessage>> optionalPaymentOutboxMessages = outboxPaymentRepository.findByTypeAndOutboxStatusAndSagaStatuses(
                SagaName.ORDER_PROCESSING_SAGA.name(),
                OutboxStatus.STARTED,
                SagaStatus.STARTED,
                SagaStatus.COMPENSATING
        );
        if (optionalPaymentOutboxMessages.isPresent() && !optionalPaymentOutboxMessages.get().isEmpty()) {
            List<PaymentOutboxMessage> paymentOutboxMessages = optionalPaymentOutboxMessages.get();
            log.info("Sending {} OrderPaymentOutboxMessage to messages bus. The ids: {}",
                    paymentOutboxMessages.size(),
                    paymentOutboxMessages.stream()
                            .map(outboxMessage -> outboxMessage.getId().toString())
                            .collect(Collectors.joining(",")));
            paymentOutboxMessages.forEach(outboxMessage -> {
                paymentMessageRequestPublisher.publish(
                        outboxMessage,
                        this::updateOutboxStatus
                );
            });
        }
    }
}
