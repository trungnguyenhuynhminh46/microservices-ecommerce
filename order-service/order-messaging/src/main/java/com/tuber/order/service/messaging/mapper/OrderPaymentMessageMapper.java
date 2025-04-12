package com.tuber.order.service.messaging.mapper;

import com.tuber.kafka.order.avro.model.PaymentOrderStatus;
import com.tuber.kafka.order.avro.model.PaymentRequestAvroModel;
import com.tuber.order.service.domain.outbox.model.payment.PaymentPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderPaymentMessageMapper {

    @Mappings({
            @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())"),
            @Mapping(target = "sagaId", source = "sagaId"),
            @Mapping(target = "orderId", source = "paymentPayload.orderId"),
            @Mapping(target = "customerId", source = "paymentPayload.customerId"),
            @Mapping(target = "price", source = "paymentPayload.price"),
            @Mapping(target = "createdAt", source = "paymentPayload.createdAt"),
            @Mapping(target = "paymentOrderStatus", source = "paymentPayload.paymentOrderStatus",
                    qualifiedByName = "stringToPaymentOrderStatus")
    })
    PaymentRequestAvroModel orderPaymentPayloadToPaymentRequestAvroModel(PaymentPayload paymentPayload, UUID sagaId);

    @Named("stringToPaymentOrderStatus")
    default PaymentOrderStatus stringToPaymentOrderStatus(String status) {
        return PaymentOrderStatus.valueOf(status);
    }
}
