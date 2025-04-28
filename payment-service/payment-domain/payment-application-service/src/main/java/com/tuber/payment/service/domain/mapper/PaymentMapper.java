package com.tuber.payment.service.domain.mapper;

import com.tuber.application.helper.CommonHelper;
import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.domain.exception.DomainException;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.dto.message.broker.PaymentRequest;
import com.tuber.payment.service.domain.entity.Payment;
import com.tuber.payment.service.domain.event.PaymentEvent;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;
import com.tuber.payment.service.domain.outbox.model.order.PaymentResponsePayload;
import com.tuber.domain.valueobject.enums.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.tuber.saga.order.SagaName.ORDER_PROCESSING_SAGA;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class PaymentMapper {
    @Autowired
    CommonHelper commonHelper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract Payment paymentRequestToPayment(PaymentRequest paymentRequest);

    @Mapping(target = "paymentId", source = "payment.id")
    @Mapping(target = "orderId", source = "payment.orderId")
    @Mapping(target = "customerId", source = "payment.customerId")
    @Mapping(target = "totalPrice", source = "payment.totalPrice")
    @Mapping(target = "finalPrice", source = "payment.finalPrice")
    @Mapping(target = "paymentStatus", source = "payment.paymentStatus")
    public abstract PaymentResponsePayload paymentEventToPaymentResponseEventPayload(PaymentEvent paymentEvent);

    public OutboxOrderMessage paymentResponsePayloadToOutboxOrderMessage(PaymentResponsePayload paymentResponsePayload, PaymentStatus paymentStatus, OutboxStatus outboxStatus, UUID sagaId) {
        OrderResponseCode responseCode = new OrderResponseCode(String.format("Could not create OrderPaymentEventPayload object for order id: %s",
                paymentResponsePayload.getOrderId()));
        DomainException throwable = new OrderDomainException(responseCode, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return OutboxOrderMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(paymentResponsePayload.getCreatedAt())
                .processedAt(LocalDate.now())
                .type(ORDER_PROCESSING_SAGA.name())
                .payload(commonHelper.mapObjectIntoString(
                        paymentResponsePayload,
                        throwable))
                .paymentStatus(paymentStatus)
                .outboxStatus(outboxStatus)
                .build();
    }

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }

    protected BigDecimal map(Money money) {
        return money.getAmount();
    }

    protected Money map(BigDecimal amount) {
        return new Money(amount);
    }
}
