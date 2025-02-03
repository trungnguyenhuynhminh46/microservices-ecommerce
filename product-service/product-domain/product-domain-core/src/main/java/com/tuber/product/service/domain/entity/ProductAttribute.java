package com.tuber.product.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.id.LongId;

import java.util.*;

public class ProductAttribute extends BaseEntity<LongId> {
    private UUID productId;
    private String name;
    private String options;

    private ProductAttribute(Builder builder) {
        super.setId(builder.id);
        setProductId(builder.productId);
        setName(builder.name);
        setOptions(builder.options);
    }

    public static Builder builder() {
        return new Builder();
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

    public Set<String> getOptions() {
        return Set.of(options.split(","));
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public static final class Builder {
        private LongId id;
        private UUID productId;
        private String name;
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

        public Builder options(String val) {
            options = val;
            return this;
        }

        public ProductAttribute build() {
            return new ProductAttribute(this);
        }
    }
}
