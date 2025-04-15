package com.tuber.order.service.dataaccess.order.adapter;

import com.tuber.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.tuber.order.service.dataaccess.order.repository.OrderJpaRepository;
import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.ports.output.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderRepositoryImpl implements OrderRepository {
    OrderJpaRepository orderJpaRepository;
    OrderDataAccessMapper orderDataAccessMapper;

    @Override
    public OrderEntity save(OrderEntity order) {
        return orderDataAccessMapper.productJpaEntityToProductEntity(
                orderJpaRepository.save(orderDataAccessMapper.productEntityToProductJpaEntity(order)
                )
        );
    }

    @Override
    public Optional<OrderEntity> findById(UUID id) {
        return orderJpaRepository.findById(id)
                .map(orderDataAccessMapper::productJpaEntityToProductEntity);
    }
}
