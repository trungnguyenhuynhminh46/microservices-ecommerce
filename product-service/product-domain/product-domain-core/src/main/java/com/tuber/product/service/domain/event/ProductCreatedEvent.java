package com.tuber.product.service.domain.event;

import com.tuber.domain.event.DomainEvent;
import com.tuber.product.service.domain.entity.Product;

public class ProductCreatedEvent implements DomainEvent<Product> {
    private final Product product;

    public ProductCreatedEvent(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
