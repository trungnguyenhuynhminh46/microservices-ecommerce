package com.tuber.order.service.domain;

import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.event.OrderCreatedEvent;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitializeOrder(OrderEntity order);
}
