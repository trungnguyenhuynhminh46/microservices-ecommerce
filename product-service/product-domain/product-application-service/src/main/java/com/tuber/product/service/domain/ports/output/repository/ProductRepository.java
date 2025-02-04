package com.tuber.product.service.domain.ports.output.repository;

import com.tuber.product.service.domain.entity.Product;

public interface ProductRepository {
    Product save(Product product);
}
