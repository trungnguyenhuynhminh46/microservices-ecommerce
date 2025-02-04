package com.tuber.product.service.domain;

import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.event.ProductCategoryCreatedEvent;
import com.tuber.product.service.domain.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductDomainServiceImpl implements ProductDomainService {
    @Override
    public ProductCategoryCreatedEvent validateAndInitializeProductCategory(ProductCategory productCategory) {
        productCategory.validateProductCategory();
        productCategory.initializeProductCategory();
        log.info("Product category with id: {} is initiated", productCategory.getCode());
        return new ProductCategoryCreatedEvent(productCategory);
    }

    @Override
    public ProductCreatedEvent validateAndInitializeProduct(Product product) {
        return null;
    }
}
