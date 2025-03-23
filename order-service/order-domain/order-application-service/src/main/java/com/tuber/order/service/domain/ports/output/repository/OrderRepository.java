package com.tuber.order.service.domain.ports.output.repository;

import com.tuber.order.service.domain.entity.OrderEntity;

public interface OrderRepository {
    OrderEntity save(OrderEntity order);
}
