package com.tuber.order.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.order.service.domain.valueobject.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Voucher extends BaseEntity<UniqueUUID> {
    private String code;
    private DiscountType discountType;
    private Money discountAmount;
    private BigDecimal discountPercentage;
    private Money minimumOrderAmount;
    private Money maximumDiscountAmount;
    private LocalDate expiryDate;
    private Integer remain;
    private Boolean active;
    private LocalDate createdAt;

    private Voucher(Builder builder) {
        setId(builder.id);
        setCode(builder.code);
        setDiscountType(builder.discountType);
        setDiscountAmount(builder.discountAmount);
        setDiscountPercentage(builder.discountPercentage);
        setMinimumOrderAmount(builder.minimumOrderAmount);
        setMaximumDiscountAmount(builder.maximumDiscountAmount);
        setExpiryDate(builder.expiryDate);
        setRemain(builder.remain);
        setActive(builder.active);
        setCreatedAt(builder.createdAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void setId(UUID id) {
        super.setId(new UniqueUUID(id));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public Money getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Money discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Money getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(Money minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public Money getMaximumDiscountAmount() {
        return maximumDiscountAmount;
    }

    public void setMaximumDiscountAmount(Money maximumDiscountAmount) {
        this.maximumDiscountAmount = maximumDiscountAmount;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


    public static final class Builder {
        private UUID id;
        private String code;
        private DiscountType discountType;
        private Money discountAmount;
        private BigDecimal discountPercentage;
        private Money minimumOrderAmount;
        private Money maximumDiscountAmount;
        private LocalDate expiryDate;
        private Integer remain;
        private Boolean active;
        private LocalDate createdAt;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder code(String val) {
            code = val;
            return this;
        }

        public Builder discountType(DiscountType val) {
            discountType = val;
            return this;
        }

        public Builder discountAmount(Money val) {
            discountAmount = val;
            return this;
        }

        public Builder discountPercentage(BigDecimal val) {
            discountPercentage = val;
            return this;
        }

        public Builder minimumOrderAmount(Money val) {
            minimumOrderAmount = val;
            return this;
        }

        public Builder maximumDiscountAmount(Money val) {
            maximumDiscountAmount = val;
            return this;
        }

        public Builder expiryDate(LocalDate val) {
            expiryDate = val;
            return this;
        }

        public Builder remain(Integer val) {
            remain = val;
            return this;
        }

        public Builder active(Boolean val) {
            active = val;
            return this;
        }

        public Builder createdAt(LocalDate val) {
            createdAt = val;
            return this;
        }

        public Voucher build() {
            return new Voucher(this);
        }
    }
}
