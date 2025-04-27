package com.tuber.inventory.service.domain.outbox.scheduler;

import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import com.tuber.inventory.service.domain.ports.output.message.publisher.InventoryConfirmationResponsePublisher;
import com.tuber.inventory.service.domain.ports.output.repository.outbox.OrderOutboxRepository;
import com.tuber.outbox.AbstractOutboxDispatcher;
import com.tuber.outbox.OutboxStatus;
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
public class OrderOutboxDispatcher extends AbstractOutboxDispatcher<OrderOutboxMessage> {
    OrderOutboxRepository orderOutboxRepository;
    InventoryConfirmationResponsePublisher inventoryConfirmationResponsePublisher;

    @Override
    protected Optional<List<OrderOutboxMessage>> findOutboxMessages() {
        return orderOutboxRepository.findByTypeAndOutboxStatus(
                SagaName.ORDER_PROCESSING_SAGA.name(),
                OutboxStatus.STARTED
        );
    }

    @Override
    protected void publishMessage(OrderOutboxMessage message, BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback) {
        inventoryConfirmationResponsePublisher.publish(message, this::updateOutboxStatus);
    }

    @Override
    protected String getMessageId(OrderOutboxMessage message) {
        return message.getId().toString();
    }

    @Override
    protected void updateOutboxStatus(OrderOutboxMessage message, OutboxStatus outboxStatus) {
        message.setOutboxStatus(outboxStatus);
        orderOutboxRepository.save(message);
        log.info("Updated OrderOutboxMessage with new outbox status: {}", outboxStatus.name());
    }

    @Scheduled(fixedDelayString = "${config-data.fixed-delay-string}",
            initialDelayString = "${config-data.init-delay-string}")
    public void scheduleDispatchOutboxMessages() {
        dispatchOutboxMessage();
    }
}
