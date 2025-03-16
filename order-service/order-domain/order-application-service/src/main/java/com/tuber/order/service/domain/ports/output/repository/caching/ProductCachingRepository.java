package com.tuber.order.service.domain.ports.output.repository.caching;

import com.nimbusds.jose.util.Pair;
import com.tuber.order.service.domain.entity.Product;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ProductCachingRepository {
    Map<Pair<UUID, String>, Product> getProductsByIds(Set<Pair<UUID, String>> productIds);
    Product updateProduct(Product product);
    void deleteByIdAndSku(Pair<UUID, String> productId);
}
