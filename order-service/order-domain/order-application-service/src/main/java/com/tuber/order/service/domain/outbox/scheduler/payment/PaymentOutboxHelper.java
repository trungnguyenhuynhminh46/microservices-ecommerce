package com.tuber.order.service.domain.outbox.scheduler.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.order.service.domain.outbox.model.payment.OrderPaymentEventPayload;
import com.tuber.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.tuber.order.service.domain.ports.output.repository.PaymentOutboxRepository;
import com.tuber.order.service.domain.valueobject.enums.OrderStatus;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.tuber.saga.order.SagaConstants.ORDER_SAGA_NAME;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PaymentOutboxHelper {
    PaymentOutboxRepository paymentOutboxRepository;
    ObjectMapper objectMapper;

    @Transactional
    protected void save(OrderPaymentOutboxMessage orderPaymentOutboxMessage) {
        OrderPaymentOutboxMessage savedMessage = paymentOutboxRepository.save(orderPaymentOutboxMessage);
        if (savedMessage == null) {
            log.error("Could not save OrderPaymentOutboxMessage with outbox id: {}", orderPaymentOutboxMessage.getId());
            throw new OrderDomainException(OrderResponseCode.FAILED_TO_SAVE_PAYMENT_OUTBOX, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        log.info("OrderPaymentOutboxMessage saved with outbox id: {}", orderPaymentOutboxMessage.getId());
    }

    private String createPayload(OrderPaymentEventPayload orderPaymentEventPayload) {
        try {
            return objectMapper.writeValueAsString(orderPaymentEventPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create OrderPaymentEventPayload object for order id: {}",
                    orderPaymentEventPayload.getOrderId(), e);
            OrderResponseCode responseCode = new OrderResponseCode(String.format("Could not create OrderPaymentEventPayload object for order id: %s",
                    orderPaymentEventPayload.getOrderId()));
            throw new OrderDomainException(responseCode, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Transactional
    public void savePaymentOutboxMessage(OrderPaymentEventPayload paymentEventPayload,
                                         OrderStatus orderStatus,
                                         SagaStatus sagaStatus,
                                         OutboxStatus outboxStatus,
                                         UUID sagaId) {
        save(OrderPaymentOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(paymentEventPayload.getCreatedAt())
                .type(ORDER_SAGA_NAME)
                .payload(createPayload(paymentEventPayload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build());
    }
}
