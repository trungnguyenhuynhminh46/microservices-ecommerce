package com.tuber.order.service.messaging.mapper;

import com.tuber.kafka.order.avro.model.PaymentRequestAvroModel;
import com.tuber.kafka.order.avro.model.PaymentResponseAvroModel;
import com.tuber.order.service.domain.dto.message.broker.PaymentResponse;
import com.tuber.order.service.domain.outbox.model.payment.PaymentPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderPaymentMessageMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    PaymentRequestAvroModel orderPaymentPayloadToPaymentRequestAvroModel(PaymentPayload paymentPayload, UUID sagaId);

    PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel paymentResponseAvroModel);
}
