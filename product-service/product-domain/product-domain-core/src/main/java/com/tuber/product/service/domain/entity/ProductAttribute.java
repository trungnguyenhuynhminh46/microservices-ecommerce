package com.tuber.product.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.id.UniqueUUID;

import java.util.*;

public class ProductAttribute extends BaseEntity<UniqueUUID> {
    private String name;
    private String options;

    private ProductAttribute(Builder builder) {
        super.setId(builder.id);
        setName(builder.name);
        setOptions(builder.options);
    }

    public static Builder builder() {
        return new Builder();
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

    public void addOption(String option) {
        if (options == null || options.isEmpty()) {
            setOptions(option);
            return;
        }
        options += "," + option;
    }

    public void removeOption(String option) {
        Set<String> currentOptions = new HashSet<>(Arrays.asList(options.split(",")));
        currentOptions.remove(option);
        options = String.join(",", currentOptions);
    }


    public static final class Builder {
        private UniqueUUID id;
        private String name;
        private String options;

        private Builder() {
        }

        public Builder id(UniqueUUID val) {
            id = val;
            return this;
        }

        public Builder id(UUID val) {
            id = new UniqueUUID(val);
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
