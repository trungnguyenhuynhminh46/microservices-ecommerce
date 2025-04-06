package com.tuber.payment.service.domain.mapper;

import com.tuber.payment.service.domain.dto.message.broker.PaymentRequest;
import com.tuber.payment.service.domain.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper {
    //TODO: Implement this method
    public abstract Payment paymentRequestToPayment(PaymentRequest paymentRequest);
}
