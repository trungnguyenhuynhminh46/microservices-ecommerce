package com.tuber.order.service.domain.ports.output.message.publisher.payment;

import com.tuber.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.tuber.outbox.OutboxStatus;

import java.util.function.BiConsumer;

//TODO: Implement this interface
public interface PaymentMessageRequestPublisher {
    void publish(OrderPaymentOutboxMessage orderPaymentOutboxMessage,
                 BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback);
}
