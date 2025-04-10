package com.tuber.payment.service.domain.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.application.helper.CommonHelper;
import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.dto.message.broker.PaymentRequest;
import com.tuber.payment.service.domain.entity.Payment;
import com.tuber.payment.service.domain.event.PaymentEvent;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;
import com.tuber.payment.service.domain.outbox.model.order.PaymentResponsePayload;
import com.tuber.payment.service.domain.valueobject.enums.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import static com.tuber.saga.order.SagaName.ORDER_PROCESSING_SAGA;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class PaymentMapper {
    @Autowired
    ObjectMapper objectMapper;

    //TODO: Implement this method
    public abstract Payment paymentRequestToPayment(PaymentRequest paymentRequest);

    //TODO: Implement this method
    public abstract PaymentResponsePayload paymentEventToPaymentResponseEventPayload(PaymentEvent paymentEvent);

    public OutboxOrderMessage paymentResponsePayloadToOutboxOrderMessage(PaymentResponsePayload paymentResponsePayload, PaymentStatus paymentStatus, OutboxStatus outboxStatus, UUID sagaId) {
        return OutboxOrderMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(paymentResponsePayload.getCreatedAt())
                .processedAt(LocalDate.now())
                .type(ORDER_PROCESSING_SAGA.name())
                .payload(createPayload(paymentResponsePayload))
                .paymentStatus(paymentStatus)
                .outboxStatus(outboxStatus)
                .build();
    }

    protected String createPayload(PaymentResponsePayload paymentPayload) {
        try {
            return objectMapper.writeValueAsString(paymentPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create order payment event payload object for order with order id: {}",
                    paymentPayload.getOrderId(), e);
            OrderResponseCode responseCode = new OrderResponseCode(String.format("Could not create OrderPaymentEventPayload object for order id: %s",
                    paymentPayload.getOrderId()));
            throw new OrderDomainException(responseCode, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
