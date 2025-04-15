package com.tuber.order.service.domain.outbox.scheduler.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.order.service.domain.outbox.model.payment.PaymentPayload;
import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import com.tuber.order.service.domain.ports.output.repository.OutboxPaymentRepository;
import com.tuber.domain.valueobject.enums.OrderStatus;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;
import com.tuber.saga.order.SagaName;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OutboxPaymentHelper {
    OutboxPaymentRepository outboxPaymentRepository;
    ObjectMapper objectMapper;

    private String createPayload(PaymentPayload paymentPayload) {
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

    private PaymentOutboxMessage createOutboxMessage(PaymentPayload paymentPayload,
                                                     OrderStatus orderStatus,
                                                     SagaStatus sagaStatus,
                                                     OutboxStatus outboxStatus,
                                                     UUID sagaId) {
        return PaymentOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(paymentPayload.getCreatedAt())
                .type(SagaName.ORDER_PROCESSING_SAGA.name())
                .payload(createPayload(paymentPayload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build();
    }

    @Transactional
    public void savePaymentOutboxMessage(PaymentPayload paymentEventPayload,
                                         OrderStatus orderStatus,
                                         SagaStatus sagaStatus,
                                         OutboxStatus outboxStatus,
                                         UUID sagaId) {
        PaymentOutboxMessage paymentOutboxMessage = createOutboxMessage(paymentEventPayload, orderStatus, sagaStatus, outboxStatus, sagaId);
        PaymentOutboxMessage savedMessage = outboxPaymentRepository.save(createOutboxMessage(paymentEventPayload, orderStatus, sagaStatus, outboxStatus, sagaId));
        if (savedMessage == null) {
            log.error("Could not save out box payment message with outbox id: {}", paymentOutboxMessage.getId());
            throw new OrderDomainException(OrderResponseCode.FAILED_TO_SAVE_PAYMENT_OUTBOX, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        log.info("Could not save out box payment message with outbox id: {}", paymentOutboxMessage.getId());
    }
}
