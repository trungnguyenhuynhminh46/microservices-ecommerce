package com.tuber.payment.service.messaging.mapper;

import com.tuber.kafka.order.avro.model.PaymentRequestAvroModel;
import com.tuber.kafka.order.avro.model.PaymentResponseAvroModel;
import com.tuber.payment.service.domain.dto.message.broker.PaymentRequest;
import com.tuber.payment.service.domain.outbox.model.order.PaymentResponsePayload;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class PaymentMessageMapper {
    //TODO: Implement this method
    public abstract PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel);
    //TODO: Implement this method
    public abstract PaymentResponseAvroModel paymentResponsePayloadToPaymentResponseAvroModel(PaymentResponsePayload paymentResponsePayload, UUID sagaId);
}
