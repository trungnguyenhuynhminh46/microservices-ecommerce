package com.tuber.order.service.domain;

import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.event.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService{
    @Override
    public OrderCreatedEvent validateAndInitializeOrder(OrderEntity order) {
        return new OrderCreatedEvent(order.selfValidate().selfInitialize(), LocalDate.now());
    }

    @Override
    public OrderPaymentCompleteEvent completeOrderPayment(OrderEntity order) {
        order.pay();
        log.info("Order with id: {} is paid", order.getId().getValue());
        return new OrderPaymentCompleteEvent(order, LocalDate.now());
    }

    @Override
    public OrderCancelEvent cancelOrder(OrderEntity order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cancelled", order.getId().getValue());
        return new OrderCancelEvent(order, LocalDate.now());
    }

    @Override
    public OrderBeginCancellingEvent beginCancellingOrder(OrderEntity order, List<String> failureMessages) {
        order.initCancelling(failureMessages);
        log.info("Start to revert order payment for order with order id: {}", order.getId().getValue());
        return new OrderBeginCancellingEvent(order, LocalDate.now());
    }

    @Override
    public OrderConfirmValidForFulfillment confirmValidForFulfillment(OrderEntity order) {
        order.confirmReadyForFulfillment();
        log.info("Order with id: {} is ready for fulfillment", order.getId().getValue());
        return new OrderConfirmValidForFulfillment(order, LocalDate.now());
    }
}
