package com.tuber.order.service.domain.ports.input.message.listener.inventory;

import com.tuber.order.service.domain.dto.message.broker.PaymentResponse;

public interface PaymentResponseListener {
    void updateOrderAfterPaymentCompleted(PaymentResponse paymentResponse);
    void updateOrderAfterPaymentCancelled(PaymentResponse paymentResponse);
}
