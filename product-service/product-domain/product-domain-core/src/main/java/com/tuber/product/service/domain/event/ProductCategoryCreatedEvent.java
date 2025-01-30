package com.tuber.product.service.domain.event;

import com.tuber.domain.event.DomainEvent;
import com.tuber.product.service.domain.entity.ProductCategory;

public class ProductCategoryCreatedEvent implements DomainEvent<ProductCategory> {
    private final ProductCategory productCategory;

    public ProductCategoryCreatedEvent(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }
}
