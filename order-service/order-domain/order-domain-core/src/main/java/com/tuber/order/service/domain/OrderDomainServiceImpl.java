package com.tuber.order.service.domain;

import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.event.OrderCreatedEvent;

public class OrderDomainServiceImpl implements OrderDomainService{
    @Override
    public OrderCreatedEvent validateAndInitializeOrder(OrderEntity order) {
        return new OrderCreatedEvent(order.selfValidate().selfInitialize());
    }
}
