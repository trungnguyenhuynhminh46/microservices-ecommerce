package com.tuber.product.service.domain;

import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.event.ProductCategoryCreatedEvent;

public interface ProductDomainService {
    public ProductCategoryCreatedEvent validateAndInitializeProductCategory(ProductCategory productCategory);
}
