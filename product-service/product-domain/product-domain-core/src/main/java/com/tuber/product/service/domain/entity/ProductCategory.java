package com.tuber.product.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.domain.constant.response.code.ProductResponseCode;
import com.tuber.domain.exception.ProductDomainException;
import com.tuber.product.service.domain.helper.SlugGenerator;

import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;

public class ProductCategory extends BaseEntity<UniqueUUID> {
    private String code;
    private String name;
    private String description;
    LocalDate createdAt;
    LocalDate updatedAt;

    private ProductCategory(Builder builder) {
        super.setId(builder.id);
        setCode(builder.code);
        setName(builder.name);
        setDescription(builder.description);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        private UniqueUUID id;
        private String code;
        private String name;
        private String description;
        private LocalDate createdAt;
        private LocalDate updatedAt;

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

        public Builder code(String val) {
            code = val;
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

    public boolean isValidForInitialization() {
        return getName() != null && getId() == null;
    }

    public void validateForInitialization() {
        if(!isValidForInitialization()) {
            throw new ProductDomainException(ProductResponseCode.PRODUCT_CATEGORY_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
    }

    public void initialize() {
        setId(new UniqueUUID(UUID.randomUUID()));
        setCode(generateCode(getName()));
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());
    }

    public static String generateCode(String categoryName) {
        String slug = SlugGenerator.slug(categoryName);
        String base64 = Base64.getEncoder().encodeToString(categoryName.getBytes());
        return slug + "-" + base64;
    }
}
