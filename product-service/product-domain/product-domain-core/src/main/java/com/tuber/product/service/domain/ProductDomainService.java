package com.tuber.product.service.domain;

import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.event.ProductCategoryCreatedEvent;
import com.tuber.product.service.domain.event.ProductCreatedEvent;

public interface ProductDomainService {
    public ProductCategoryCreatedEvent validateAndInitializeProductCategory(ProductCategory productCategory);
    public ProductCreatedEvent validateAndInitializeProduct(Product product);
}
