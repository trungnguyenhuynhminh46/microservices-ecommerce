package com.tuber.inventory.service.dataaccess.caching.product.adapter;

import com.tuber.inventory.service.domain.entity.Product;
import com.tuber.inventory.service.domain.ports.output.repository.cache.ProductCachingRepository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ProductCachingRepositoryImpl implements ProductCachingRepository {
    @Override
    public Map<UUID, Product> getProductsByIds(Set<UUID> productIds) {
        return null;
    }

    @Override
    public Set<Product> saveAll(Set<Product> products) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}
