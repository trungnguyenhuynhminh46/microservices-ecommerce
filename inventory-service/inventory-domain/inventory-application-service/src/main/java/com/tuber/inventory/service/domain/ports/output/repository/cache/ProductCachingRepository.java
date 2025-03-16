package com.tuber.inventory.service.domain.ports.output.repository.cache;

import com.tuber.inventory.service.domain.entity.Product;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ProductCachingRepository {
    Map<UUID, Product> getProductsByIds(Set<UUID> productIds);
    Set<Product> saveAll(Set<Product> products);
    Product save(Product product);
    void deleteById(UUID id);
}
