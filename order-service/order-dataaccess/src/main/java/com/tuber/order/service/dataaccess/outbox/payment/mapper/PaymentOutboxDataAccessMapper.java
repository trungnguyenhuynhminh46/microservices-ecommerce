package com.tuber.order.service.dataaccess.outbox.payment.mapper;

import com.tuber.order.service.dataaccess.outbox.payment.entity.PaymentOutboxMessageJpaEntity;
import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PaymentOutboxDataAccessMapper {
    public abstract PaymentOutboxMessageJpaEntity paymentOutboxMessageToPaymentOutboxMessageJpaEntity(PaymentOutboxMessage paymentOutboxMessage);

    public abstract PaymentOutboxMessage paymentOutboxMessageJpaEntityToPaymentOutboxMessage(PaymentOutboxMessageJpaEntity paymentOutboxMessageJpaEntity);
}
