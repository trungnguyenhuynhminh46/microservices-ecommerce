package com.tuber.order.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.order.service.domain.ports.dto.order.CreateOrderCommand;
import com.tuber.order.service.domain.ports.dto.order.OrderResponseData;

public class OrderApplicationServiceImpl implements OrderApplicationService {
    @Override
    public ApiResponse<OrderResponseData> createOrder(CreateOrderCommand createOrderCommand) {
        return null;
    }
}
