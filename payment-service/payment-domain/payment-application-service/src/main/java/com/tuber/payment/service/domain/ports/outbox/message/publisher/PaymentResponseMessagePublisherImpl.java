package com.tuber.payment.service.domain.ports.outbox.message.publisher;

import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;

import java.util.function.BiConsumer;

public class PaymentResponseMessagePublisherImpl implements PaymentResponseMessagePublisher {
    //TODO: Implement this method
    @Override
    public void publish(OutboxOrderMessage outboxOrderMessage, BiConsumer<OutboxOrderMessage, OutboxStatus> onSuccessOutbox) {

    }
}
