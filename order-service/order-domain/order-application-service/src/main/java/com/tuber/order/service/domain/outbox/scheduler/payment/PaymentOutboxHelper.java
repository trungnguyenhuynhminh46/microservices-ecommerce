package com.tuber.order.service.domain.outbox.scheduler.payment;

import com.tuber.application.helper.CommonHelper;
import com.tuber.application.mapper.StatusMapper;
import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.domain.exception.DomainException;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.order.service.domain.outbox.model.payment.PaymentPayload;
import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import com.tuber.order.service.domain.ports.output.repository.outbox.OutboxPaymentRepository;
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

import java.time.LocalDate;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PaymentOutboxHelper {
    OutboxPaymentRepository outboxPaymentRepository;
    StatusMapper statusMapper;
    CommonHelper commonHelper;

    private PaymentOutboxMessage createOutboxMessage(PaymentPayload paymentPayload,
                                                     OrderStatus orderStatus,
                                                     SagaStatus sagaStatus,
                                                     OutboxStatus outboxStatus,
                                                     UUID sagaId) throws DomainException {
        OrderDomainException throwable = new OrderDomainException(
                new OrderResponseCode(String.format("Could not create OrderPaymentEventPayload object for order id: %s",
                        paymentPayload.getOrderId())), HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return PaymentOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(paymentPayload.getCreatedAt())
                .type(SagaName.ORDER_PROCESSING_SAGA.name())
                .payload(commonHelper.mapObjectIntoString(paymentPayload, throwable))
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
            throw new OrderDomainException(OrderResponseCode.FAILED_TO_SAVE_PAYMENT_OUTBOX, HttpStatus.INTERNAL_SERVER_ERROR.value(), paymentEventPayload.getOrderId());
        }
        log.info("Could not save out box payment message with outbox id: {}", paymentOutboxMessage.getId());
    }

    public void updatePaymentOutboxMessage(
            PaymentOutboxMessage message,
            OrderStatus orderStatus
    ) {
        message.setProcessedAt(LocalDate.now());
        message.setOrderStatus(orderStatus);
        message.setSagaStatus(statusMapper.orderStatusToSagaStatus(orderStatus));
        outboxPaymentRepository.save(message);
    }
}
