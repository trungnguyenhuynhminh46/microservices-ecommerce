package com.tuber.payment.service.domain;

import com.tuber.payment.service.domain.entity.CreditEntry;
import com.tuber.payment.service.domain.entity.CreditHistory;
import com.tuber.payment.service.domain.entity.Payment;
import com.tuber.payment.service.domain.event.PaymentEvent;

import java.util.List;

//TODO: Implement this class
public class PaymentDomainServiceImpl implements PaymentDomainService {
    @Override
    public PaymentEvent validateAndInitializePayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        return null;
    }

    @Override
    public PaymentEvent validateAndCancelPayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        return null;
    }
}
