package com.tuber.payment.service.domain.event;

import com.tuber.domain.event.DomainEvent;
import com.tuber.payment.service.domain.entity.Payment;

import java.time.LocalDate;
import java.util.List;

public class PaymentEvent implements DomainEvent<Payment> {
    private final Payment payment;
    private final LocalDate createdAt;
    private final List<String> failureMessages;

    public PaymentEvent(Payment payment, LocalDate createdAt, List<String> failureMessages) {
        this.payment = payment;
        this.createdAt = createdAt;
        this.failureMessages = failureMessages;
    }

    public Payment getPayment() {
        return payment;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }
}
