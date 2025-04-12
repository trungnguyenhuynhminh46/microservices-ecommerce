package com.tuber.payment.service.domain.ports.output.repository;

import com.tuber.payment.service.domain.entity.Payment;

import java.util.Optional;
import java.util.UUID;

//TODO Implement this class
public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findByOrderId(UUID orderId);
}
