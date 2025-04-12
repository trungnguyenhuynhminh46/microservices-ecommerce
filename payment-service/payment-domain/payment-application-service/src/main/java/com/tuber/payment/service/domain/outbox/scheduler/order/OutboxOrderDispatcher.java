package com.tuber.payment.service.domain.outbox.scheduler.order;

import com.tuber.outbox.OutboxSchedulerDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//TODO: Implement this class
@Slf4j
@Component
public class OutboxOrderDispatcher implements OutboxSchedulerDispatcher {
    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${config-data.fixed-delay-string}",
            initialDelayString = "${config-data.init-delay-string}")
    public void dispatchOutboxMessage() {

    }
}
