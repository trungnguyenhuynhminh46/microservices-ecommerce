package com.tuber.order.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.entity.ProductAttribute;
import com.tuber.domain.entity.Warehouse;
import com.tuber.domain.valueobject.Money;
import com.tuber.order.service.domain.valueobject.ProductId;

import java.util.List;
import java.util.UUID;

public class Product extends AggregateRoot<ProductId> {
    List<ProductAttribute> attributes;
    String name;
    Money basePrice;
    Money finalPrice;
    Warehouse warehouse;

    private Product(Builder builder) {
        super.setId(builder.id);
        attributes = builder.attributes;
        name = builder.name;
        basePrice = builder.basePrice;
        finalPrice = builder.finalPrice;
        warehouse = builder.warehouse;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ProductId id;
        private List<ProductAttribute> attributes;
        private String name;
        private Money basePrice;
        private Money finalPrice;
        private Warehouse warehouse;

        private Builder() {
        }

        public Builder id(ProductId val) {
            id = val;
            return this;
        }

        public Builder id(UUID productId, String sku) {
            id = new ProductId(productId, sku);
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

        public Builder warehouse(Warehouse val) {
            warehouse = val;
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

    public Money getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Money basePrice) {
        this.basePrice = basePrice;
    }

    public Money getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Money finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
