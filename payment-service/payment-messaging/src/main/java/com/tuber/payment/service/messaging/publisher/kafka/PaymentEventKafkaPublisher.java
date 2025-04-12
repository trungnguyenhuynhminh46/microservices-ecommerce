package com.tuber.payment.service.messaging.publisher.kafka;

import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;
import com.tuber.payment.service.domain.ports.output.message.publisher.PaymentResponseMessagePublisher;

import java.util.function.BiConsumer;

public class PaymentEventKafkaPublisher implements PaymentResponseMessagePublisher {
    //TODO: Implement this method
    @Override
    public void publish(OutboxOrderMessage outboxOrderMessage, BiConsumer<OutboxOrderMessage, OutboxStatus> onSuccessOutbox) {

    }
}
