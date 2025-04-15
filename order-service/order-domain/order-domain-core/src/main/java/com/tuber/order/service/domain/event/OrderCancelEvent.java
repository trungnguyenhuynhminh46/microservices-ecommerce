package com.tuber.order.service.domain.event;

import com.tuber.order.service.domain.entity.OrderEntity;

import java.time.LocalDate;

public class OrderCancelEvent extends OrderEvent {
    public OrderCancelEvent(OrderEntity order, LocalDate createdAt) {
        super(order, createdAt);
    }
}
