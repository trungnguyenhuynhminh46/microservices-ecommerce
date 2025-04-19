package com.tuber.order.service.domain.outbox.scheduler.payment;

import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import com.tuber.order.service.domain.ports.output.repository.outbox.OutboxPaymentRepository;
import com.tuber.outbox.OutboxSchedulerCleaner;
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
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OutboxPaymentCleaner implements OutboxSchedulerCleaner {
    OutboxPaymentRepository outboxPaymentRepository;

    @Override
    @Scheduled(cron = "@midnight")
    public void cleanOutboxMessage() {
        Optional<List<PaymentOutboxMessage>> optionalOrderPaymentOutboxMessage =
                outboxPaymentRepository.findByTypeAndOutboxStatusAndSagaStatuses(
                        SagaName.ORDER_PROCESSING_SAGA.name(),
                        OutboxStatus.COMPLETED,
                        SagaStatus.SUCCEEDED,
                        SagaStatus.FAILED,
                        SagaStatus.COMPENSATED
                );

        if(optionalOrderPaymentOutboxMessage.isPresent() && !optionalOrderPaymentOutboxMessage.get().isEmpty()) {
            List<PaymentOutboxMessage> paymentOutboxMessages = optionalOrderPaymentOutboxMessage.get();
            log.info("Received {} OrderPaymentOutboxMessage for clean-up. The ids: {}",
                    paymentOutboxMessages.size(),
                    paymentOutboxMessages.stream()
                            .map(outboxMessage -> outboxMessage.getId().toString())
                            .collect(Collectors.joining(",")));
            outboxPaymentRepository.deleteByTypeAndOutboxStatusAndSagaStatuses(
                    SagaName.ORDER_PROCESSING_SAGA.name(),
                    OutboxStatus.COMPLETED,
                    SagaStatus.SUCCEEDED,
                    SagaStatus.FAILED,
                    SagaStatus.COMPENSATED
            );
            log.info("{} OrderPaymentOutboxMessage deleted!", paymentOutboxMessages.size());
        }
    }
}
