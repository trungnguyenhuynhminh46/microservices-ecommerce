package com.tuber.inventory.service.domain.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.id.LongId;

import java.util.UUID;

public class ProductAttribute extends BaseEntity<LongId> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private UUID productId;
    private String name;
    private String defaultValue;
    private String options;

    private ProductAttribute(Builder builder) {
        super.setId(builder.id);
        setProductId(builder.productId);
        setName(builder.name);
        setDefaultValue(builder.defaultValue);
        setOptions(builder.options);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getAttributeId() {
        return super.getId().getValue();
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public static final class Builder {
        private LongId id;
        private UUID productId;
        private String name;
        private String defaultValue;
        private String options;

        private Builder() {
        }

        public Builder id(Long val) {
            id = new LongId(val);
            return this;
        }

        public Builder id(LongId val) {
            id = val;
            return this;
        }

        public Builder productId(UUID val) {
            productId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder defaultValue(String val) {
            defaultValue = val;
            return this;
        }

        public Builder options(String val) {
            options = val;
            return this;
        }

        public ProductAttribute build() {
            return new ProductAttribute(this);
        }
    }


}
