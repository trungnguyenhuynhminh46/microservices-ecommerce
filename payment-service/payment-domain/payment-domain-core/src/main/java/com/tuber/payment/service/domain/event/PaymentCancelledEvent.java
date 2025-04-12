package com.tuber.payment.service.domain.event;

import com.tuber.payment.service.domain.entity.Payment;

import java.time.LocalDate;
import java.util.List;

public class PaymentCancelledEvent extends PaymentEvent {
    public PaymentCancelledEvent(Payment payment, LocalDate createdAt, List<String> failureMessages) {
        super(payment, createdAt, failureMessages);
    }
}
