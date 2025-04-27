package com.tuber.inventory.service.domain.outbox.scheduler;

import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import com.tuber.inventory.service.domain.ports.output.repository.outbox.OrderOutboxRepository;
import com.tuber.outbox.OutboxSchedulerCleaner;
import com.tuber.outbox.OutboxStatus;
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
public class OrderOutboxCleaner implements OutboxSchedulerCleaner {
    OrderOutboxRepository orderOutboxRepository;

    @Override
    @Transactional
    @Scheduled(cron = "@midnight")
    public void cleanOutboxMessage() {
        Optional<List<OrderOutboxMessage>> optionalOrderOutboxMessages =
                orderOutboxRepository.findByTypeAndOutboxStatus(
                        SagaName.ORDER_PROCESSING_SAGA.name(),
                        OutboxStatus.COMPLETED
                );

        if (optionalOrderOutboxMessages.isPresent() && !optionalOrderOutboxMessages.get().isEmpty()) {
            List<OrderOutboxMessage> outboxMessages = optionalOrderOutboxMessages.get();
            log.info("Received {} OrderOutboxMessage for clean-up. The ids: {}",
                    outboxMessages.size(),
                    outboxMessages.stream()
                            .map(outboxMessage -> outboxMessage.getId().toString())
                            .collect(Collectors.joining(",")));
            orderOutboxRepository.deleteByTypeAndOutboxStatus(
                    SagaName.ORDER_PROCESSING_SAGA.name(),
                    OutboxStatus.COMPLETED
            );
            log.info("{} OrderOutboxMessage deleted!", outboxMessages.size());
        }
    }
}
