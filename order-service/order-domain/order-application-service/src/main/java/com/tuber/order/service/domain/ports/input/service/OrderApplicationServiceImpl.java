package com.tuber.order.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.order.service.domain.dto.order.CreateOrderCommand;
import com.tuber.order.service.domain.dto.order.OrderResponseData;
import com.tuber.order.service.domain.handler.order.CreateOrderHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderApplicationServiceImpl implements OrderApplicationService {
    CreateOrderHandler createOrderHandler;
    @Override
    public ApiResponse<OrderResponseData> createOrder(CreateOrderCommand createOrderCommand) {
        return createOrderHandler.createOrder(createOrderCommand);
    }
}
