package com.tuber.domain.valueobject.valueobject;

import java.util.Objects;

public class Price {
    private final Money basePrice;
    private final Double discount;
    private final Double tax;
    private final Money shippingCost;
    private final Money finalPrice;

    private Money calculateFinalPrice() {
        Money discountAmount = basePrice.multiply(discount);
        Money taxAmount = basePrice.multiply(tax);
        return basePrice.subtract(discountAmount).add(taxAmount).add(shippingCost);
    }

    public Price(Money basePrice, Double discount, Double tax, Money shippingCost) {
        if(basePrice == null) {
            throw new IllegalArgumentException("Base price cannot be null");
        }
        if(discount != null && (discount < 0 || discount > 1)) {
            throw new IllegalArgumentException("Discount cannot be less than 0 or greater than 1");
        }
        if(tax != null && (tax < 0 || tax > 1)) {
            throw new IllegalArgumentException("Tax cannot be less than 0 or greater than 1");
        }
        if(shippingCost != null && shippingCost.isSmallerThanZero()) {
            throw new IllegalArgumentException("Shipping cost cannot be less than 0");
        }
        this.basePrice = basePrice;
        this.discount = (discount != null) ? discount : 0;
        this.tax = (tax != null) ? tax : 0;
        this.shippingCost = (shippingCost != null) ? shippingCost : Money.ZERO;
        this.finalPrice = calculateFinalPrice();
    }

    private Price(Builder builder) {
        this(builder.basePrice, builder.discount, builder.tax, builder.shippingCost);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Money getBasePrice() {
        return basePrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public Double getTax() {
        return tax;
    }

    public Money getShippingCost() {
        return shippingCost;
    }

    public Money getFinalPrice() {
        return finalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(basePrice, price.basePrice) && Objects.equals(discount, price.discount) && Objects.equals(tax, price.tax) && Objects.equals(shippingCost, price.shippingCost) && Objects.equals(finalPrice, price.finalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basePrice, discount, tax, shippingCost, finalPrice);
    }


    public static final class Builder {
        private Money basePrice;
        private Double discount;
        private Double tax;
        private Money shippingCost;
        private Money finalPrice;

        private Builder() {
        }

        public Builder basePrice(Money val) {
            basePrice = val;
            return this;
        }

        public Builder discount(Double val) {
            discount = val;
            return this;
        }

        public Builder tax(Double val) {
            tax = val;
            return this;
        }

        public Builder shippingCost(Money val) {
            shippingCost = val;
            return this;
        }

        public Builder finalPrice(Money val) {
            finalPrice = val;
            return this;
        }

        public Price build() {
            return new Price(this);
        }
    }
}
