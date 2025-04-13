package com.tuber.payment.service.dataaccess.payment.adapter;

import com.tuber.payment.service.domain.entity.Payment;
import com.tuber.payment.service.domain.ports.output.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

//TODO Implement this class
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentRepositoryImpl implements PaymentRepository {
    @Override
    public Payment save(Payment payment) {
        return null;
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return Optional.empty();
    }
}
