package com.tuber.order.service.domain.ports.output.repository;

import com.tuber.order.service.domain.entity.OrderEntity;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    OrderEntity save(OrderEntity order);
    Optional<OrderEntity> findById(UUID id);
}
