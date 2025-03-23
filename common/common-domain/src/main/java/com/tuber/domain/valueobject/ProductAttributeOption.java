package com.tuber.domain.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductAttributeOption {
    private final String name;
    private final BigDecimal changeAmount;

    public ProductAttributeOption(String name, BigDecimal changeAmount) {
        this.name = name;
        this.changeAmount = changeAmount;
    }

    private ProductAttributeOption(Builder builder) {
        name = builder.name;
        changeAmount = builder.changeAmount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public static final class Builder {
        private String name;
        private BigDecimal changeAmount;

        private Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder changeAmount(BigDecimal val) {
            changeAmount = val;
            return this;
        }

        public ProductAttributeOption build() {
            return new ProductAttributeOption(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAttributeOption that = (ProductAttributeOption) o;
        return Objects.equals(name, that.name) && Objects.equals(changeAmount, that.changeAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, changeAmount);
    }
}
