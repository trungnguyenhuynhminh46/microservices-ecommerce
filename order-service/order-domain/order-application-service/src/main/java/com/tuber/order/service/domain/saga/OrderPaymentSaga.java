package com.tuber.order.service.domain.saga;

import com.tuber.order.service.domain.dto.message.broker.PaymentResponse;
import com.tuber.saga.SagaStep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//TODO: Implement this class
@Slf4j
@Component
public class OrderPaymentSaga implements SagaStep<PaymentResponse> {
    @Override
    public void process(PaymentResponse data) {

    }

    @Override
    public void rollback(PaymentResponse data) {

    }
}
