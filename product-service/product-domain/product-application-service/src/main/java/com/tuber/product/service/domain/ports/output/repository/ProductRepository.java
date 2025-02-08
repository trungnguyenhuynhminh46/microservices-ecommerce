package com.tuber.product.service.domain.ports.output.repository;

import com.tuber.product.service.domain.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Boolean existsById(UUID id);
    Product findById(UUID id);
    List<Product> findAll();
}
