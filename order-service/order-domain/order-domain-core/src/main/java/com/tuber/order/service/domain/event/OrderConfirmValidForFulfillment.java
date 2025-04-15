package com.tuber.order.service.domain.event;

import com.tuber.order.service.domain.entity.OrderEntity;

import java.time.LocalDate;

public class OrderConfirmValidForFulfillment extends OrderEvent {
    public OrderConfirmValidForFulfillment(OrderEntity order, LocalDate createdAt) {
        super(order, createdAt);
    }
}
