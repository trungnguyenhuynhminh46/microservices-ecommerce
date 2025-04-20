package com.tuber.payment.service.domain.ports.input.message.listener;

import com.tuber.payment.service.domain.dto.message.broker.PaymentRequest;
import com.tuber.payment.service.domain.helper.PaymentMessageHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener{
    PaymentMessageHelper paymentMessageHelper;
    @Override
    public void acceptPayment(PaymentRequest paymentRequest) {
        paymentMessageHelper.persistPayment(paymentRequest);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        paymentMessageHelper.persistCancelledPayment(paymentRequest);
    }
}
