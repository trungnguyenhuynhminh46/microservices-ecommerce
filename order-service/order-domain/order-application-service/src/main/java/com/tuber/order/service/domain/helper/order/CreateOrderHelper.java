package com.tuber.order.service.domain.helper.order;

import com.tuber.application.handler.ApiResponse;
import com.tuber.order.service.domain.dto.order.CreateOrderCommand;
import com.tuber.order.service.domain.dto.order.OrderResponseData;
import com.tuber.order.service.domain.entity.Product;
import com.tuber.order.service.domain.helper.CommonOrderHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateOrderHelper {
    CommonOrderHelper commonOrderHelper;

    private List<Product> cachedProducts;

    public ApiResponse<OrderResponseData> createOrder(CreateOrderCommand createOrderCommand) {
        // Get product id list
        // Check if products data is stale => refresh products data in cache
        // Convert CreateOrderCommand to Order entity
        // Validate order entity
        // Save order entity
        // Convert Order entity to OrderResponseData
        return null;
    }
}
