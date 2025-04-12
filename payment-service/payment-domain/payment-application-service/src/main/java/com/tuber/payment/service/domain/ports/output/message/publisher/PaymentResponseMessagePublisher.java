package com.tuber.payment.service.domain.ports.output.message.publisher;

import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;

import java.util.function.BiConsumer;

public interface PaymentResponseMessagePublisher {
    void publish(OutboxOrderMessage outboxOrderMessage, BiConsumer<OutboxOrderMessage, OutboxStatus> onSuccessOutbox);
}
