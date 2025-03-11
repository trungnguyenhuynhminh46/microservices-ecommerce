package com.tuber.order.service.domain;

import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.event.OrderCreatedEvent;

public class OrderDomainServiceImpl implements OrderDomainService{
    @Override
    public OrderCreatedEvent validateAndInitializeOrder(OrderEntity order) {
        // TODO Implement this method
        return null;
    }
}
