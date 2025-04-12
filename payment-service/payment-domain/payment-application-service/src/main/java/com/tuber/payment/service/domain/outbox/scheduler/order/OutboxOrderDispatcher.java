package com.tuber.payment.service.domain.outbox.scheduler.order;

import com.tuber.outbox.AbstractOutboxDispatcher;
import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;
import com.tuber.payment.service.domain.ports.output.message.publisher.PaymentResponseMessagePublisher;
import com.tuber.payment.service.domain.ports.output.repository.OutboxOrderRepository;
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
public class OutboxOrderDispatcher extends AbstractOutboxDispatcher<OutboxOrderMessage> {
    OutboxOrderRepository outboxOrderRepository;
    PaymentResponseMessagePublisher paymentResponseMessagePublisher;

    @Override
    protected Optional<List<OutboxOrderMessage>> findOutboxMessages() {
        return outboxOrderRepository.findByTypeAndOutboxStatus(
                SagaName.ORDER_PROCESSING_SAGA.name(),
                OutboxStatus.STARTED
        );
    }

    @Override
    protected void publishMessage(OutboxOrderMessage message, BiConsumer<OutboxOrderMessage, OutboxStatus> outboxCallback) {
        paymentResponseMessagePublisher.publish(message, this::updateOutboxStatus);
    }

    @Override
    protected String getMessageId(OutboxOrderMessage message) {
        return message.getId().toString();
    }

    @Override
    protected void updateOutboxStatus(OutboxOrderMessage message, OutboxStatus outboxStatus) {
        message.setOutboxStatus(outboxStatus);
        outboxOrderRepository.save(message);
        log.info("Updated OutboxOrderMessage with new outbox status: {}", outboxStatus.name());
    }

    @Scheduled(fixedDelayString = "${config-data.fixed-delay-string}",
            initialDelayString = "${config-data.init-delay-string}")
    public void scheduleDispatchOutboxMessages() {
        dispatchOutboxMessage();
    }
}
