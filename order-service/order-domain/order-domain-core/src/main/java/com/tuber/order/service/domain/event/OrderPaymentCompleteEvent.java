package com.tuber.order.service.domain.event;

import com.tuber.order.service.domain.entity.OrderEntity;

import java.time.LocalDate;

public class OrderPaymentCompleteEvent extends OrderEvent {
    public OrderPaymentCompleteEvent(OrderEntity order, LocalDate createdAt) {
        super(order, createdAt);
    }
}
