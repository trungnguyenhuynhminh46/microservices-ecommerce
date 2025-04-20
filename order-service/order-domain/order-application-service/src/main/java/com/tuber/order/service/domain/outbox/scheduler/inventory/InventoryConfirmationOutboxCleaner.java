package com.tuber.order.service.domain.outbox.scheduler.inventory;

import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import com.tuber.order.service.domain.ports.output.repository.outbox.InventoryConfirmationOutboxRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryConfirmationOutboxCleaner implements OutboxSchedulerCleaner {
    InventoryConfirmationOutboxRepository inventoryOrderRepository;

    @Override
    @Transactional
    @Scheduled(cron = "@midnight")
    public void cleanOutboxMessage() {
        Optional<List<InventoryConfirmationOutboxMessage>> response =
                inventoryOrderRepository.findByTypeAndOutboxStatusAndSagaStatusIn(
                        SagaName.ORDER_PROCESSING_SAGA.name(),
                        OutboxStatus.COMPLETED,
                        SagaStatus.SUCCEEDED,
                        SagaStatus.FAILED,
                        SagaStatus.COMPENSATED
                );

        if (response.isPresent() && !response.get().isEmpty()) {
            List<InventoryConfirmationOutboxMessage> outboxMessages = response.get();
            log.info("Received {} InventoryConfirmationOutboxMessage for clean-up. The ids: {}",
                    outboxMessages.size(),
                    outboxMessages.stream()
                            .map(outboxMessage -> outboxMessage.getId().toString())
                            .collect(Collectors.joining(",")));
            inventoryOrderRepository.deleteByTypeAndOutboxStatusAndSagaStatusIn(
                    SagaName.ORDER_PROCESSING_SAGA.name(),
                    OutboxStatus.COMPLETED,
                    SagaStatus.SUCCEEDED,
                    SagaStatus.FAILED,
                    SagaStatus.COMPENSATED
            );
            log.info("{} InventoryConfirmationOutboxMessage deleted!", outboxMessages.size());
        }
    }
}
