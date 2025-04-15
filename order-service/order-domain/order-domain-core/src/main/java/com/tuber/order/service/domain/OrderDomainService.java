package com.tuber.order.service.domain;

import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.event.OrderCreatedEvent;
import com.tuber.order.service.domain.event.OrderPaymentCancelEvent;
import com.tuber.order.service.domain.event.OrderPaymentCompleteEvent;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitializeOrder(OrderEntity order);
    OrderPaymentCompleteEvent completeOrderPayment(OrderEntity order);
    OrderPaymentCancelEvent cancelOrderPayment(OrderEntity order);

}
