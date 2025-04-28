package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.entity.ProductAttribute;
import com.tuber.domain.util.ProductUtility;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Product extends AggregateRoot<UniqueUUID> {
    private ProductUtility productUtility = new ProductUtility();
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

    public Boolean validateSku(
            String sku
    ) {
        return validateSku(sku, false);
    }

    public Boolean validateSku(String sku, Boolean throwError) {
        Map<String, String> mapAssignedAttributes = productUtility.decodeSkuToAttributes(sku);
        List<ProductAttribute> attributes = getAttributes();

        boolean isValid = mapAssignedAttributes.keySet().stream()
                .allMatch(name -> attributes.stream().anyMatch(attribute -> attribute.getName().equals(name)));

        if (!isValid && throwError) {
            throw new IllegalArgumentException("Invalid SKU: contains attributes not defined in the product.");
        }

        return isValid;
    }
}
