package com.tuber.order.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.order.service.domain.ports.dto.order.CreateOrderCommand;
import com.tuber.order.service.domain.ports.dto.order.OrderResponseData;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    ApiResponse<OrderResponseData> createOrder(@Valid CreateOrderCommand createOrderCommand);
}
