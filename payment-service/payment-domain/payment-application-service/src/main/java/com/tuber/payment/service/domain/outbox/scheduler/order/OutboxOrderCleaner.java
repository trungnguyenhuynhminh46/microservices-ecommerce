package com.tuber.payment.service.domain.outbox.scheduler.order;

import com.tuber.outbox.OutboxSchedulerCleaner;
import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;
import com.tuber.payment.service.domain.ports.output.repository.OutboxOrderRepository;
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
public class OutboxOrderCleaner implements OutboxSchedulerCleaner {
    OutboxOrderRepository outboxOrderRepository;

    @Override
    @Transactional
    @Scheduled(cron = "@midnight")
    public void cleanOutboxMessage() {
        Optional<List<OutboxOrderMessage>> optionalOrderOutboxMessage =
                outboxOrderRepository.findByTypeAndOutboxStatus(
                        SagaName.ORDER_PROCESSING_SAGA.name(),
                        OutboxStatus.COMPLETED
                );

        if (optionalOrderOutboxMessage.isPresent() && !optionalOrderOutboxMessage.get().isEmpty()) {
            List<OutboxOrderMessage> outboxMessages = optionalOrderOutboxMessage.get();
            log.info("Received {} OutboxOrderMessage for clean-up. The ids: {}",
                    outboxMessages.size(),
                    outboxMessages.stream()
                            .map(outboxMessage -> outboxMessage.getId().toString())
                            .collect(Collectors.joining(",")));
            outboxOrderRepository.deleteByTypeAndOutboxStatus(
                    SagaName.ORDER_PROCESSING_SAGA.name(),
                    OutboxStatus.COMPLETED
            );
            log.info("{} OutboxOrderMessage deleted!", outboxMessages.size());
        }
    }
}
