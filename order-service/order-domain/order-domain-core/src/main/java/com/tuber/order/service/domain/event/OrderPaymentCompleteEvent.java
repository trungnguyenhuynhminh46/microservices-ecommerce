package com.tuber.order.service.domain.event;

import com.tuber.domain.event.DomainEvent;
import com.tuber.order.service.domain.entity.OrderEntity;

public class OrderPaymentCompleteEvent implements DomainEvent<OrderEntity> {
    private final OrderEntity order;

    public OrderPaymentCompleteEvent(OrderEntity order) {
        this.order = order;
    }

    public OrderEntity getOrder() {
        return order;
    }
}
