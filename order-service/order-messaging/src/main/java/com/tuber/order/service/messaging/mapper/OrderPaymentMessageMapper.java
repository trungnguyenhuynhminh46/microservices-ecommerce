package com.tuber.order.service.messaging.mapper;

import com.tuber.kafka.order.avro.model.PaymentRequestAvroModel;
import com.tuber.order.service.domain.outbox.model.payment.PaymentPayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class OrderPaymentMessageMapper {
    //TODO: Implement this method
    public PaymentRequestAvroModel orderPaymentPayloadToPaymentRequestAvroModel(PaymentPayload paymentPayload) {
        return null;
    }
}
