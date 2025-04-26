package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.entity.ProductAttribute;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.domain.valueobject.ProductAssignedAttribute;

import java.util.List;
import java.util.UUID;

public class Product extends AggregateRoot<UniqueUUID> {
    private String name;
    private Money price;
    private List<ProductAttribute> attributes;

    private Product(Builder builder) {
        super.setId(builder.id);
        setName(builder.name);
        setPrice(builder.price);
        setAttributes(builder.attributes);
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getProductId() {
        return getId().getValue();
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

    public List<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
    }

    public static final class Builder {
        private UniqueUUID id;
        private String name;
        private Money price;
        private List<ProductAttribute> attributes;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = new UniqueUUID(val);
            return this;
        }

        public Builder id(UniqueUUID val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder attributes(List<ProductAttribute> val) {
            attributes = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    public static void validateSku(String sku) {
        //TODO: Implement this method
    }

    public static Money getPriceFromSku(String sku) {
        //TODO: Implement this method
        return null;
    }

    public static Money getPriceFromAttributes(List<ProductAttribute> attributes) {
        //TODO: Implement this method
        return null;
    }
}
