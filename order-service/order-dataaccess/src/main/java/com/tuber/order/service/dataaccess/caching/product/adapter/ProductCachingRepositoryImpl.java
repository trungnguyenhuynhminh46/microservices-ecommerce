package com.tuber.order.service.dataaccess.caching.product.adapter;

import com.nimbusds.jose.util.Pair;
import com.tuber.order.service.domain.entity.Product;
import com.tuber.order.service.domain.ports.output.repository.caching.ProductCachingRepository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ProductCachingRepositoryImpl implements ProductCachingRepository {

    @Override
    public Map<Pair<UUID, String>, Product> getProductsByIds(Set<Pair<UUID, String>> productIds) {
        //TODO: Implement this method
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        //TODO: Implement this method
        return null;
    }

    @Override
    public void deleteByIdAndSku(Pair<UUID, String> productId) {
        //TODO: Implement this method
    }
}
