package com.tuber.product.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.domain.valueobject.valueobject.Money;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.exception.ProductDomainException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Product extends AggregateRoot<UniqueUUID> {
    private String name;
    private Money price;
    private String description;
    private String tags;
    private Float rating;
    private UniqueUUID categoryId;
    private List<ProductAttribute> attributes;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private Product(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        price = builder.price;
        description = builder.description;
        tags = builder.tags;
        rating = builder.rating;
        categoryId = builder.categoryId;
        attributes = builder.attributes;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void setId(UUID id) {
        super.setId(new UniqueUUID(id));
    }


    public static final class Builder {
        private UniqueUUID id;
        private String name;
        private Money price;
        private String description;
        private String tags;
        private Float rating;
        private UniqueUUID categoryId;
        private List<ProductAttribute> attributes;
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

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder price(BigDecimal val) {
            price = new Money(val);
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder tags(String val) {
            tags = val;
            return this;
        }

        public Builder rating(Float val) {
            rating = val;
            return this;
        }

        public Builder categoryId(UniqueUUID val) {
            categoryId = val;
            return this;
        }

        public Builder categoryId(UUID val) {
            categoryId = new UniqueUUID(val);
            return this;
        }

        public Builder attributes(List<ProductAttribute> val) {
            attributes = val;
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

        public Product build() {
            return new Product(this);
        }
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

    public void setPrice(BigDecimal price) {
        this.price = new Money(price);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public UUID getCategoryId() {
        return categoryId.getValue();
    }

    public void setCategoryId(UniqueUUID categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = new UniqueUUID(categoryId);
    }

    public List<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
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

    public boolean isValidForInitialization() {
        return getName() != null && getPrice() != null && getId() != null;
    }

    public void validateProduct() {
        if (!isValidForInitialization()) {
            throw new ProductDomainException(ProductResponseCode.PRODUCT_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
    }
}
