package com.tuber.order.service.domain.helper;

import com.nimbusds.jose.util.Pair;
import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.entity.Product;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonOrderHelper {
    private Set<Pair<UUID, String>> getNotCachedProductIds(Map<Pair<UUID, String>, Product> cachedProducts) {
        return cachedProducts.entrySet().stream()
                .filter(entry -> entry.getValue() == null)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public Set<Product> getProductsAndRefreshCachedProducts(Set<Pair<UUID, String>> productIds) {
        //TODO: Implement this method
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
