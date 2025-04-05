package com.tuber.order.service.domain.outbox.scheduler.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.order.service.domain.outbox.model.payment.OrderPaymentPayload;
import com.tuber.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.tuber.order.service.domain.ports.output.repository.OutboxPaymentRepository;
import com.tuber.order.service.domain.valueobject.enums.OrderStatus;
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
public class PaymentOutboxHelper {
    OutboxPaymentRepository outboxPaymentRepository;
    ObjectMapper objectMapper;

    private String createPayload(OrderPaymentPayload orderPaymentPayload) {
        try {
            return objectMapper.writeValueAsString(orderPaymentPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create order payment event payload object for order with order id: {}",
                    orderPaymentPayload.getOrderId(), e);
            OrderResponseCode responseCode = new OrderResponseCode(String.format("Could not create OrderPaymentEventPayload object for order id: %s",
                    orderPaymentPayload.getOrderId()));
            throw new OrderDomainException(responseCode, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private OrderPaymentOutboxMessage createOutboxMessage(OrderPaymentPayload orderPaymentPayload,
                                                          OrderStatus orderStatus,
                                                          SagaStatus sagaStatus,
                                                          OutboxStatus outboxStatus,
                                                          UUID sagaId) {
        return OrderPaymentOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(orderPaymentPayload.getCreatedAt())
                .type(SagaName.ORDER_PROCESSING_SAGA.name())
                .payload(createPayload(orderPaymentPayload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build();
    }

    @Transactional
    public void savePaymentOutboxMessage(OrderPaymentPayload paymentEventPayload,
                                         OrderStatus orderStatus,
                                         SagaStatus sagaStatus,
                                         OutboxStatus outboxStatus,
                                         UUID sagaId) {
        OrderPaymentOutboxMessage orderPaymentOutboxMessage = createOutboxMessage(paymentEventPayload, orderStatus, sagaStatus, outboxStatus, sagaId);
        OrderPaymentOutboxMessage savedMessage = outboxPaymentRepository.save(createOutboxMessage(paymentEventPayload, orderStatus, sagaStatus, outboxStatus, sagaId));
        if (savedMessage == null) {
            log.error("Could not save out box payment message with outbox id: {}", orderPaymentOutboxMessage.getId());
            throw new OrderDomainException(OrderResponseCode.FAILED_TO_SAVE_PAYMENT_OUTBOX, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        log.info("Could not save out box payment message with outbox id: {}", orderPaymentOutboxMessage.getId());
    }
}
