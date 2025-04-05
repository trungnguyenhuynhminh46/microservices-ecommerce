package com.tuber.order.service.domain.outbox.scheduler.payment;

import com.tuber.outbox.OutboxSchedulerCleaner;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OutboxPaymentCleaner implements OutboxSchedulerCleaner {
    PaymentOutboxHelper paymentOutboxHelper;
    @Override
    @Scheduled(cron = "@midnight")
    public void cleanOutboxMessage() {

    }
}
