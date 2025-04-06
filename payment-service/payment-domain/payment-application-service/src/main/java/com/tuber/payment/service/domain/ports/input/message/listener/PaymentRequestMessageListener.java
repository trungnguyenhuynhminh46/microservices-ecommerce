package com.tuber.payment.service.domain.ports.input.message.listener;

import com.tuber.payment.service.domain.dto.message.broker.PaymentRequest;

public interface PaymentRequestMessageListener {
    void acceptPayment(PaymentRequest paymentRequest);

    void cancelPayment(PaymentRequest paymentRequest);
}
