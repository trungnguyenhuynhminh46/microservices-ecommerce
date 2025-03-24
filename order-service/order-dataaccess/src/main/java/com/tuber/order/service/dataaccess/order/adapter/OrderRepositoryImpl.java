package com.tuber.order.service.dataaccess.order.adapter;

import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.ports.output.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public OrderEntity save(OrderEntity order) {
        return null;
    }
}
