package com.tuber.payment.service.dataaccess.payment.adapter;

import com.tuber.payment.service.dataaccess.payment.mapper.PaymentDataAccessMapper;
import com.tuber.payment.service.dataaccess.payment.repository.PaymentJpaRepository;
import com.tuber.payment.service.domain.entity.Payment;
import com.tuber.payment.service.domain.ports.output.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentRepositoryImpl implements PaymentRepository {
    PaymentJpaRepository paymentJpaRepository;
    PaymentDataAccessMapper paymentDataAccessMapper;

    @Override
    public Payment save(Payment payment) {
        return paymentDataAccessMapper.paymentJpaEntityToPayment(
                paymentJpaRepository.save(
                        paymentDataAccessMapper.paymentToPaymentJpaEntity(payment)
                )
        );
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return paymentJpaRepository.findByOrderId(orderId)
                .map(paymentDataAccessMapper::paymentJpaEntityToPayment);
    }
}
