package com.tuber.inventory.service.domain.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductOption {
    private final String name;
    private final BigDecimal changeAmount;

    public ProductOption(String name, BigDecimal changeAmount) {
        this.name = name;
        this.changeAmount = changeAmount;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOption that = (ProductOption) o;
        return Objects.equals(name, that.name) && Objects.equals(changeAmount, that.changeAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, changeAmount);
    }
}
