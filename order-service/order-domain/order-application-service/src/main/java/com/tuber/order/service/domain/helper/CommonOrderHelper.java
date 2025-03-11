package com.tuber.order.service.domain.helper;

import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.entity.Product;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonOrderHelper {
    public Set<Product> getProductsAndRefreshCachedProducts(Set<UUID> productIds) {
        //TODO: Implement this method
        // Get products updated time in cache
        // Confirm if products data is stale through feign client
        // Get back the data. If stale, refresh products data in cache
        // Return products
        return null;
    }

    public OrderEntity saveOrder(OrderEntity order) {
        //TODO: Implement this method
        return null;
    }

    public void checkIfVouchersAreValid(Set<UUID> voucherIds) {
        //TODO: Implement this method
    }
}
