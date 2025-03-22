package com.tuber.order.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.entity.ProductAttribute;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;

import java.util.List;
import java.util.UUID;

public class Product extends AggregateRoot<UniqueUUID> {
    String name;
    Money price;
    List<ProductAttribute> attributes;

    private Product(Builder builder) {
        super.setId(builder.id);
        attributes = builder.attributes;
        name = builder.name;
        price = builder.basePrice;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UniqueUUID id;
        private List<ProductAttribute> attributes;
        private String name;
        private Money basePrice;
        private Money finalPrice;

        private Builder() {
        }

        public Builder id(UniqueUUID val) {
            id = val;
            return this;
        }

        public Builder id(UUID productId) {
            id = new UniqueUUID(productId);
            return this;
        }

        public Builder attributes(List<ProductAttribute> val) {
            attributes = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder basePrice(Money val) {
            basePrice = val;
            return this;
        }

        public Builder finalPrice(Money val) {
            finalPrice = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    public List<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
}
