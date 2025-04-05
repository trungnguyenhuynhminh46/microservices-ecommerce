package com.tuber.order.service.domain.outbox.scheduler.payment;

import com.tuber.outbox.OutboxSchedulerDispatcher;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OutboxPaymentDispatcher implements OutboxSchedulerDispatcher {
    PaymentOutboxHelper paymentOutboxHelper;
    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${service.messages.fixed-delay-string}",
            initialDelayString = "${service.messages.init-delay-string}")
    public void dispatchOutboxMessage() {

    }
}
