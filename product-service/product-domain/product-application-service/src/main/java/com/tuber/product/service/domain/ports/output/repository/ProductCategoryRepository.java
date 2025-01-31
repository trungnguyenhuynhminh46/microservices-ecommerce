package com.tuber.product.service.domain.ports.output.repository;

import com.tuber.product.service.domain.entity.ProductCategory;

import java.util.Set;

public interface ProductCategoryRepository {
    ProductCategory save(ProductCategory productCategory);
    Boolean existsByCode(String code);
    ProductCategory findByCode(String code);
    Set<ProductCategory> findAll();
}
