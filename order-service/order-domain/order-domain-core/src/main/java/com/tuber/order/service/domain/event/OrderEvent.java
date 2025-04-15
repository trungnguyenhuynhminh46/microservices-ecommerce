package com.tuber.order.service.domain.event;

import com.tuber.domain.event.DomainEvent;
import com.tuber.order.service.domain.entity.OrderEntity;

import java.time.LocalDate;

public class OrderEvent implements DomainEvent<OrderEntity> {
    private final OrderEntity order;
    private final LocalDate createdAt;

    public OrderEvent(OrderEntity order, LocalDate createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
