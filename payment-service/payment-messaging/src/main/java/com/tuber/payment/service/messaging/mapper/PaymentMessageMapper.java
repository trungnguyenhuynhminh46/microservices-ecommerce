package com.tuber.payment.service.messaging.mapper;

import com.tuber.kafka.order.avro.model.PaymentRequestAvroModel;
import com.tuber.payment.service.domain.dto.message.broker.PaymentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PaymentMessageMapper {
    //TODO: Implement this method
    public abstract PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel);
}
