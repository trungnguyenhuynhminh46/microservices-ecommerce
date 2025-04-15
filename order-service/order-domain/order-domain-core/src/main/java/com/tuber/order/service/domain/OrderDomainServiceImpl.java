package com.tuber.order.service.domain;

import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.event.OrderCreatedEvent;
import com.tuber.order.service.domain.event.OrderPaymentCancelEvent;
import com.tuber.order.service.domain.event.OrderPaymentCompleteEvent;

public class OrderDomainServiceImpl implements OrderDomainService{
    @Override
    public OrderCreatedEvent validateAndInitializeOrder(OrderEntity order) {
        return new OrderCreatedEvent(order.selfValidate().selfInitialize());
    }

    //TODO: Implement this method
    @Override
    public OrderPaymentCompleteEvent completeOrderPayment(OrderEntity order) {
        return null;
    }

    //TODO: Implement this method
    @Override
    public OrderPaymentCancelEvent cancelOrderPayment(OrderEntity order) {
        return null;
    }
}
