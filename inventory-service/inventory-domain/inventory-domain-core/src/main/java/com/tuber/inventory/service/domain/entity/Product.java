package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.valueobject.Money;
import com.tuber.inventory.service.domain.constant.InventoryResponseCode;
import com.tuber.inventory.service.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.valueobject.ProductId;
import com.tuber.inventory.service.domain.valueobject.ProductIdVO;
import com.tuber.inventory.service.domain.valueobject.ProductOption;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Product extends AggregateRoot<ProductId> {
    private String name;
    private Money finalPrice;
    private Map<String, ProductOption> attributes = new HashMap<>();
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Product(ProductId productId) {
        super.setId(productId);
    }

    private Product(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        finalPrice = builder.finalPrice;
        attributes = builder.attributes;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ProductId id;
        private String name;
        private Money finalPrice;
        private Map<String, ProductOption> attributes;
        private LocalDate createdAt;
        private LocalDate updatedAt;

        private Builder() {
        }

        public Builder id(ProductId val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder finalPrice(Money val) {
            finalPrice = val;
            return this;
        }

        public Builder attributes(Map<String, ProductOption> val) {
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

    public UUID getProductId() {
        return super.getId().getValue().getProductId();
    }

    public String getSku() {
        return super.getId().getValue().getSku();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Money finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Map<String, ProductOption> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, ProductOption> attributes) {
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
        return getName() != null && getFinalPrice() != null && getId() == null && getCreatedAt() == null && getUpdatedAt() == null;
    }

    public void validateFinalPrice() {
        if (getFinalPrice() == null || getFinalPrice().isSmallerThanOrEqualToZero()) {
            throw new IllegalArgumentException("Product finalPrice can not be null and must be larger than zero");
        }
    }

    public void validateAttributes() {
        if(getAttributes() != null && !getAttributes().isEmpty()) {
            getAttributes().forEach((key, value) -> {
                if (value == null) {
                    throw new IllegalArgumentException("Product attribute value can not be null");
                }
            });
        }
    }

    private void initializeAttributes() {
        if(getAttributes() == null) {
            setAttributes(new HashMap<>());
        }
    }

    public void validateProperties() {
        validateFinalPrice();
        validateAttributes();
    }

    public String generateSku() {
        return "sku";
    }

    public void validateForInitialization() {
        if (!isValidForInitialization()) {
            throw new InventoryDomainException(InventoryResponseCode.PRODUCT_IN_WRONG_STATE_FOR_INITIALIZATION, 406);

        }
        validateProperties();
    }

    public void initialize(UUID productId) {
        setId(new ProductId(new ProductIdVO(productId, generateSku())));
        initializeAttributes();
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());
    }
}
