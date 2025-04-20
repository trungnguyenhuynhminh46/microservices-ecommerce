package com.tuber.order.service.domain.outbox.scheduler.inventory;

import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import com.tuber.order.service.domain.ports.output.message.publisher.inventory.InventoryConfirmationRequestPublisher;
import com.tuber.order.service.domain.ports.output.repository.outbox.InventoryConfirmationOutboxRepository;
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
public class InventoryConfirmationOutboxDispatcher extends AbstractOutboxDispatcher<InventoryConfirmationOutboxMessage> {
    InventoryConfirmationOutboxRepository inventoryConfirmationOutboxRepository;
    InventoryConfirmationRequestPublisher inventoryConfirmationRequestPublisher;

    @Override
    protected Optional<List<InventoryConfirmationOutboxMessage>> findOutboxMessages() {
        return inventoryConfirmationOutboxRepository.findByTypeAndOutboxStatusAndSagaStatusIn(
                SagaName.ORDER_PROCESSING_SAGA.name(),
                OutboxStatus.STARTED,
                SagaStatus.PROCESSING
        );
    }

    @Override
    protected void publishMessage(InventoryConfirmationOutboxMessage message, BiConsumer<InventoryConfirmationOutboxMessage, OutboxStatus> outboxCallback) {
        inventoryConfirmationRequestPublisher.publish(message, this::updateOutboxStatus);
    }

    @Override
    protected String getMessageId(InventoryConfirmationOutboxMessage message) {
        return message.getId().toString();
    }

    @Override
    protected void updateOutboxStatus(InventoryConfirmationOutboxMessage message, OutboxStatus outboxStatus) {
        message.setOutboxStatus(outboxStatus);
        inventoryConfirmationOutboxRepository.save(message);
        log.info("Updated InventoryConfirmationOutboxMessage with new outbox status: {}", outboxStatus.name());
    }

    @Scheduled(fixedDelayString = "${config-data.fixed-delay-string}",
            initialDelayString = "${config-data.init-delay-string}")
    public void scheduleDispatchOutboxMessages() {
        dispatchOutboxMessage();
    }
}
