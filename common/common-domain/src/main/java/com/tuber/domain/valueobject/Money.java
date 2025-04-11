package com.tuber.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        this.amount = setScale(amount);
    }

    public Money(String amount) {
        this(new BigDecimal(amount));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    private BigDecimal setScale(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Money add(Money money) {
        return new Money(amount.add(money.amount));
    }

    public Money subtract(Money money) {
        return new Money(amount.subtract(money.amount));
    }

    public Money multiply(Double multiplier) {
        return new Money(amount.multiply(setScale(new BigDecimal(multiplier))));
    }

    public int compareTo(Money money) {
        return amount.compareTo(money.amount);
    }

    public boolean isGreaterThanZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isSmallerThanZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isSmallerThanOrEqualToZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) <= 0;
    }

    public boolean isGreaterThan(Money money) {
        return amount != null && amount.compareTo(money.amount) > 0;
    }

    public boolean isGreaterThanOrEqual(Money money) {
        return amount != null && amount.compareTo(money.amount) >= 0;
    }

    public boolean isLessThanOrEqual(Money money) {
        return amount != null && amount.compareTo(money.amount) <= 0;
    }

    public boolean isLessThan(Money money) {
        return amount != null && amount.compareTo(money.amount) < 0;
    }
}
