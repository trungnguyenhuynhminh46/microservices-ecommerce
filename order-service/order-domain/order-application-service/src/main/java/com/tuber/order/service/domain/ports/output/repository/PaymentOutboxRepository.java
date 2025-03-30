package com.tuber.order.service.domain.ports.output.repository;

import com.tuber.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;

public interface PaymentOutboxRepository {
    OrderPaymentOutboxMessage save(OrderPaymentOutboxMessage orderPaymentOutboxMessage);
}
