package com.tuber.order.service.domain;

import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.event.*;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitializeOrder(OrderEntity order);
    OrderPaymentCompleteEvent completeOrderPayment(OrderEntity order);
    OrderCancelEvent cancelOrder(OrderEntity order, List<String> failureMessages);
    OrderBeginCancellingEvent beginCancellingOrder(OrderEntity order, List<String> failureMessages);
    OrderConfirmValidForFulfillment confirmValidForFulfillment(OrderEntity order);
}
