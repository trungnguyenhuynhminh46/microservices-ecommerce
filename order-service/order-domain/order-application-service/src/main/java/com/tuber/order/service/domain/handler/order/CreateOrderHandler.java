package com.tuber.order.service.domain.handler.order;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.mapper.StatusMapper;
import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.order.service.domain.dto.order.CreateOrderCommand;
import com.tuber.order.service.domain.dto.order.OrderResponseData;
import com.tuber.order.service.domain.event.OrderCreatedEvent;
import com.tuber.order.service.domain.helper.order.CreateOrderHelper;
import com.tuber.order.service.domain.mapper.OrderMapper;
import com.tuber.order.service.domain.outbox.scheduler.payment.PaymentOutboxHelper;
import com.tuber.outbox.OutboxStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateOrderHandler {
    CreateOrderHelper createOrderHelper;
    OrderMapper orderMapper;
    PaymentOutboxHelper paymentOutboxHelper;
    StatusMapper statusMapper;

    @Transactional
    public ApiResponse<OrderResponseData> createOrder(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = createOrderHelper.persistOrder(createOrderCommand);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());

        paymentOutboxHelper.savePaymentOutboxMessage(
                orderMapper.orderCreatedEventToOrderPaymentEventPayload(orderCreatedEvent),
                orderCreatedEvent.getOrder().getOrderStatus(),
                OutboxStatus.STARTED,
                UUID.randomUUID()
        );

        return ApiResponse.<OrderResponseData>builder()
                .code(OrderResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Order created successfully")
                .data(orderMapper.orderEntityToOrderResponseData(orderCreatedEvent.getOrder()))
                .build();
    }
}
