package com.tuber.payment.service.domain.outbox.scheduler.order;

import com.tuber.outbox.OutboxSchedulerCleaner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

//TODO: Implement this class
public class OutboxOrderCleaner implements OutboxSchedulerCleaner {
    @Override
    @Transactional
    @Scheduled(cron = "@midnight")
    public void cleanOutboxMessage() {

    }
}
