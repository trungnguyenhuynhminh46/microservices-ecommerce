package com.tuber.product.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.id.UniqueStringId;

import java.time.LocalDate;

public class ProductCategory extends BaseEntity<UniqueStringId> {
    private String name;
    private String description;
    LocalDate createdAt;
    LocalDate updatedAt;

    private ProductCategory(Builder builder) {
        super.setId(builder.code);
        setName(builder.name);
        setDescription(builder.description);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCode() {
        return getId().getValue();
    }

    public void setCode(String code) {
        setId(new UniqueStringId(code));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }


    public static final class Builder {
        private UniqueStringId code;
        private String name;
        private String description;
        private LocalDate createdAt;
        private LocalDate updatedAt;

        private Builder() {
        }

        public Builder code(UniqueStringId val) {
            code = val;
            return this;
        }

        public Builder code(String val) {
            code = new UniqueStringId(val);
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder createdAt(LocalDate val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(LocalDate val) {
            updatedAt = val;
            return this;
        }

        public ProductCategory build() {
            return new ProductCategory(this);
        }
    }
}
