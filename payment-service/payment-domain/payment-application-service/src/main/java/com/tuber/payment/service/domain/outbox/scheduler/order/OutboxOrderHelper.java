package com.tuber.payment.service.domain.outbox.scheduler.order;

import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.outbox.model.order.PaymentResponsePayload;
import com.tuber.payment.service.domain.valueobject.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OutboxOrderHelper {
    //TODO: Implement this method
    public void saveOrderOutboxMessage(PaymentResponsePayload paymentResponsePayload, PaymentStatus paymentStatus, OutboxStatus outboxStatus, String sagaId) {
    }
}
