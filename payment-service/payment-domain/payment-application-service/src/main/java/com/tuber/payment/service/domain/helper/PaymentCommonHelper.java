package com.tuber.payment.service.domain.helper;

import com.tuber.payment.service.domain.constant.PaymentResponseCode;
import com.tuber.payment.service.domain.entity.CreditEntry;
import com.tuber.payment.service.domain.entity.CreditHistory;
import com.tuber.payment.service.domain.entity.Payment;
import com.tuber.payment.service.domain.exception.NotFoundPaymentException;
import com.tuber.payment.service.domain.exception.PaymentDomainException;
import com.tuber.payment.service.domain.ports.output.repository.CreditEntryRepository;
import com.tuber.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import com.tuber.payment.service.domain.ports.output.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentCommonHelper {
    CreditEntryRepository creditEntryRepository;
    CreditHistoryRepository creditHistoryRepository;
    PaymentRepository paymentRepository;

    public CreditEntry verifyCreditEntryOfCustomerExists(UUID customerId) {
        Optional<CreditEntry> creditEntryResponse = creditEntryRepository.findByCustomerId(customerId);
        if (creditEntryResponse.isEmpty()) {
            throw new PaymentDomainException(PaymentResponseCode.CREDIT_ENTRY_NOT_FOUND, HttpStatus.NOT_FOUND.value(), customerId);
        }
        return creditEntryResponse.get();
    }

    public List<CreditHistory> verifyCreditHistoryOfCustomerExists(UUID customerId) {
        Optional<List<CreditHistory>> creditHistoryResponse = creditHistoryRepository.findAllByCustomerId(customerId);
        if (creditHistoryResponse.isEmpty()) {
            throw new PaymentDomainException(PaymentResponseCode.CREDIT_HISTORY_NOT_FOUND, HttpStatus.NOT_FOUND.value(), customerId);
        }
        return creditHistoryResponse.get();
    }

    public Payment verifyPaymentOfOrderExists(UUID orderId) {
        Optional<Payment> paymentResponse = paymentRepository.findByOrderId(orderId);
        if (paymentResponse.isEmpty()) {
            throw new NotFoundPaymentException(PaymentResponseCode.PAYMENT_NOT_FOUND, HttpStatus.NOT_FOUND.value(), orderId);
        }
        return paymentResponse.get();
    }
}
