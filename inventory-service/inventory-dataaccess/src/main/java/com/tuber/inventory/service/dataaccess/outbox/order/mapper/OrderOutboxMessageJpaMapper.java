package com.tuber.inventory.service.dataaccess.outbox.order.mapper;

import com.tuber.inventory.service.dataaccess.outbox.order.entity.OrderOutboxMessageJpaEntity;
import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class OrderOutboxMessageJpaMapper {
    public abstract OrderOutboxMessage orderOutboxMessageJpaEntityToOrderOutboxMessage(
            OrderOutboxMessageJpaEntity orderOutboxMessageJpaEntity);

    public abstract OrderOutboxMessageJpaEntity orderOutboxMessageToOrderOutboxMessageJpaEntity(
            OrderOutboxMessage orderOutboxMessage);
}
