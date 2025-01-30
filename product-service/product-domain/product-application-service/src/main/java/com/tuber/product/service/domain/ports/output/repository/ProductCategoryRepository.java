package com.tuber.product.service.domain.ports.output.repository;

import com.tuber.product.service.domain.entity.ProductCategory;

public interface ProductCategoryRepository {
    ProductCategory save(ProductCategory productCategory);
    Boolean existsByCode(String code);
}
