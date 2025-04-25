package com.tuber.payment.service.messaging.mapper;

import com.tuber.kafka.order.avro.model.PaymentRequestAvroModel;
import com.tuber.kafka.order.avro.model.PaymentResponseAvroModel;
import com.tuber.payment.service.domain.dto.message.broker.PaymentRequest;
import com.tuber.payment.service.domain.outbox.model.order.PaymentResponsePayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class PaymentMessageMapper {
    public abstract PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel);
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "totalPrice", source = "paymentResponsePayload.totalPrice")
    @Mapping(target = "finalPrice", source = "paymentResponsePayload.finalPrice")
    public abstract PaymentResponseAvroModel paymentResponsePayloadToPaymentResponseAvroModel(PaymentResponsePayload paymentResponsePayload, UUID sagaId);
}
