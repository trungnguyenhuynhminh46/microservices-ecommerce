package com.tuber.order.service.domain.mapper;

import com.nimbusds.jose.util.Pair;
import com.tuber.order.service.domain.dto.order.CreateOrderCommand;
import com.tuber.order.service.domain.dto.order.OrderResponseData;
import com.tuber.order.service.domain.dto.shared.OrderItemDTO;
import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    public Set<Pair<UUID, String>> orderIdDTOsToProductIdsSet(List<OrderItemDTO> orderItems) {
        //TODO: Implement this method
        return null;
    }

    public OrderEntity createOrderCommandToOrderEntity(CreateOrderCommand createOrderCommand, Set<Product> products) {
        //TODO: Implement this method
        return null;
    }

    public OrderResponseData orderEntityToOrderResponseData(OrderEntity order) {
        //TODO: Implement this method
        return null;
    }
}
