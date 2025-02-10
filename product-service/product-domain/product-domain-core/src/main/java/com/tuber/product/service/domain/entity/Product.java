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

    public Product(UUID productId) {
        super.setId(new UniqueUUID(productId));
    }

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
        this.description = description == null ? "" : description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? "" : tags;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating == null ? 0.0f : rating;
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
        this.attributes = attributes == null ? List.of() : attributes;
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
        return getName() != null && getPrice() != null && getId() == null && getRating() == null && getCreatedAt() == null && getUpdatedAt() == null;
    }

    public void validatePrice() {
        if (getPrice() == null || getPrice().isSmallerThanOrEqualToZero()) {
            throw new IllegalArgumentException("Product price can not be null and must be larger than zero");
        }
    }

    public void validateTags() {
        if (getTags() != null && getTags().matches("([a-zA-Z0-9]+,?)+")) {
            throw new IllegalArgumentException("Product tags must follow format: [tag1],[tag2],[tag3]. For example: 'electronics,smartphone,apple'");
        }
    }

    public void validateRating() {
        if (getRating() != null && (getRating() < 0 || getRating() > 5)) {
            throw new IllegalArgumentException("Product rating must be between 0 and 5");
        }
    }

    public void validateInitializeAttributes() {
        if (getAttributes() != null) {
            getAttributes().forEach(ProductAttribute::validateForInitialization);
        }
    }

    public void validateAttributes() {
        if (getAttributes() != null) {
            getAttributes().forEach(ProductAttribute::validateProperties);
        }
    }

    private void initializeDescription() {
        if(getDescription() == null) {
            setDescription("");
        }
    }

    private void initializeTags() {
        if(getTags() == null) {
            setTags("");
        }
    }

    public void initializeProductAttributes() {
        if(getAttributes() == null || getAttributes().isEmpty()) {
            setAttributes(List.of());
            return;
        }
        long attributeId = 1;
        for(ProductAttribute attribute : getAttributes()) {
            attribute.initialize(attributeId++, getId().getValue());
        }
    }

    public void validateProperties() {
        validatePrice();
        validateTags();
        validateRating();
        validateAttributes();
    }

    public void validateForInitialization() {
        if (!isValidForInitialization()) {
            throw new ProductDomainException(ProductResponseCode.PRODUCT_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
        this.validateProperties();
        validateInitializeAttributes();
    }

    public void initialize() {
        setId(new UniqueUUID(UUID.randomUUID()));
        setRating(0.0f);
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());
        initializeDescription();
        initializeTags();
        initializeProductAttributes();
    }
}
