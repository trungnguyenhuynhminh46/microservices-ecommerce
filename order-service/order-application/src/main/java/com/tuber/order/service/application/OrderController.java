package com.tuber.order.service.application;

import com.tuber.application.handler.ApiResponse;
import com.tuber.order.service.domain.dto.order.CreateOrderCommand;
import com.tuber.order.service.domain.dto.order.OrderResponseData;
import com.tuber.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/${service.name}/orders", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseData>> createOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        ApiResponse<OrderResponseData> createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        log.info("Successfully created order with id {}", createOrderResponse.getData().getId());
        return ResponseEntity.ok(createOrderResponse);
    }
}
