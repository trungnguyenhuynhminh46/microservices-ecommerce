package com.tuber.inventory.service.dataaccess.caching.product.adapter;

import com.tuber.inventory.service.dataaccess.caching.product.entity.ProductHash;
import com.tuber.inventory.service.dataaccess.caching.product.mapper.ProductHashMapper;
import com.tuber.inventory.service.dataaccess.caching.product.repository.ProductHashRepository;
import com.tuber.inventory.service.domain.entity.Product;
import com.tuber.inventory.service.domain.ports.output.repository.cache.ProductCachingRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCachingRepositoryImpl implements ProductCachingRepository {
    ProductHashRepository productHashRepository;
    ProductHashMapper productHashMapper;

    @Override
    public Map<UUID, Product> getProductsMapById(Set<UUID> productIds) {
        Set<ProductHash> productHashSet = (Set<ProductHash>) productHashRepository.findAllById(productIds);
        return productHashMapper.productHashesToProductsMap(productHashSet);
    }

    @Override
    public Set<Product> saveAll(Set<Product> products) {
        Set<ProductHash> productHashSet = products.stream()
                .map(productHashMapper::productEntityToProductHash)
                .collect(java.util.stream.Collectors.toSet());
        Set<ProductHash> savedProductHashSet = (Set<ProductHash>) productHashRepository.saveAll(productHashSet);
        return savedProductHashSet.stream()
                .map(productHashMapper::productHashToProductEntity)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public Product save(Product product) {
        return productHashMapper.productHashToProductEntity(
                productHashRepository.save(productHashMapper.productEntityToProductHash(product))
        );
    }

    @Override
    public void deleteById(UUID id) {
        productHashRepository.deleteById(id);
    }
}
