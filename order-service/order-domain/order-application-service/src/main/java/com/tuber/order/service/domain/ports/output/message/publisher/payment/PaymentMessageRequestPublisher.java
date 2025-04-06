package com.tuber.order.service.domain.ports.output.message.publisher.payment;

import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import com.tuber.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentMessageRequestPublisher {
    void publish(PaymentOutboxMessage paymentOutboxMessage,
                 BiConsumer<PaymentOutboxMessage, OutboxStatus> outboxCallback);
}
