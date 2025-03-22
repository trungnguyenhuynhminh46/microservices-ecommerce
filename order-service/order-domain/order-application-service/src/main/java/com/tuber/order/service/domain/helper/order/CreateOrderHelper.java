package com.tuber.order.service.domain.helper.order;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.helper.CommonHelper;
import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.order.service.domain.OrderDomainService;
import com.tuber.order.service.domain.dto.http.client.inventory.InternalInventoryDetailResponseData;
import com.tuber.order.service.domain.dto.order.CreateOrderCommand;
import com.tuber.order.service.domain.dto.order.OrderResponseData;
import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.event.OrderCreatedEvent;
import com.tuber.order.service.domain.helper.CommonOrderHelper;
import com.tuber.order.service.domain.mapper.OrderMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateOrderHelper {
    CommonHelper commonHelper;
    CommonOrderHelper commonOrderHelper;
    OrderMapper orderMapper;
    OrderDomainService orderDomainService;

    public ApiResponse<OrderResponseData> createOrder(CreateOrderCommand createOrderCommand) {
        Set<InternalInventoryDetailResponseData> inventoryDetails = commonOrderHelper.getSetOfInventoryDetails(
                orderMapper.orderIdDTOsToSetOfProductIdWithSkuDTO(
                        createOrderCommand.getOrderItems()
                )
        );
        OrderEntity order = orderMapper.createOrderCommandToOrderEntity(createOrderCommand, inventoryDetails, commonHelper.extractTokenSubject());
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitializeOrder(order);
        OrderEntity savedOrder = commonOrderHelper.saveOrder(orderCreatedEvent.getOrder());
        return ApiResponse.<OrderResponseData>builder()
                .code(OrderResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Order created successfully")
                .data(orderMapper.orderEntityToOrderResponseData(savedOrder))
                .build();
    }
}
