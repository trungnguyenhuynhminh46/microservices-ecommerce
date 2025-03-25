package com.tuber.order.service.dataaccess.order.repository;

import com.tuber.order.service.dataaccess.order.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {
}
