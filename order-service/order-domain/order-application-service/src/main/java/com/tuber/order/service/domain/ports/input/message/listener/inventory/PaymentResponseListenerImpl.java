package com.tuber.order.service.domain.ports.input.message.listener.inventory;

import com.tuber.order.service.domain.dto.message.broker.PaymentResponse;
import com.tuber.order.service.domain.saga.OrderPaymentSaga;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentResponseListenerImpl implements PaymentResponseListener {
    OrderPaymentSaga orderPaymentSaga;

    @Override
    public void updateOrderAfterPaymentCompleted(PaymentResponse paymentResponse) {
        orderPaymentSaga.process(paymentResponse);
        log.info("Order Payment Saga process is completed for order with order id: {}", paymentResponse.getOrderId());
    }

    @Override
    public void updateOrderAfterPaymentCancelled(PaymentResponse paymentResponse) {
        orderPaymentSaga.rollback(paymentResponse);
        log.info("Order Payment Saga rollback is completed for order with order id: {}, the failure messages: {}",
                paymentResponse.getOrderId(),
                String.join(",", paymentResponse.getFailureMessages()));
    }
}
