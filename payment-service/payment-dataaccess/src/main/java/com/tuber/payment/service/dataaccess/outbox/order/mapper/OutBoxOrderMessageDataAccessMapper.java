package com.tuber.payment.service.dataaccess.outbox.order.mapper;

import com.tuber.payment.service.dataaccess.outbox.order.entity.OutboxOrderMessageJpaEntity;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class OutBoxOrderMessageDataAccessMapper {
    public abstract OutboxOrderMessageJpaEntity outboxOrderMessageToOutboxOrderMessageJpaEntity(
            OutboxOrderMessage outboxOrderMessage);
    public abstract OutboxOrderMessage outboxOrderMessageJpaEntityToOutboxOrderMessage(
            OutboxOrderMessageJpaEntity outboxOrderMessageJpaEntity);
}
