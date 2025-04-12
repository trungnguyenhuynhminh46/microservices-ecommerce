package com.tuber.order.service.messaging.mapper;

import com.tuber.kafka.order.avro.model.PaymentRequestAvroModel;
import com.tuber.kafka.order.avro.model.PaymentOrderStatus;
import com.tuber.order.service.domain.outbox.model.payment.PaymentPayload;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class OrderPaymentMessageMapper {
    public PaymentRequestAvroModel orderPaymentPayloadToPaymentRequestAvroModel(PaymentPayload paymentPayload, UUID sagaId) {
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(sagaId)
                .setOrderId(paymentPayload.getOrderId())
                .setCustomerId(paymentPayload.getCustomerId())
                .setPrice(paymentPayload.getPrice())
                .setCreatedAt(paymentPayload.getCreatedAt())
                .setPaymentOrderStatus(PaymentOrderStatus.valueOf(paymentPayload.getPaymentOrderStatus()))
                .build();
    }
}
