package com.tuber.order.service.domain.dto.shared;

import com.tuber.order.service.domain.valueobject.DiscountType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherDetailDTO {
    UUID id;
    String code;
    DiscountType discountType;
    BigDecimal discountAmount;
    BigDecimal discountPercentage;
    BigDecimal minimumOrderAmount;
    BigDecimal maximumDiscountAmount;
    String expiryDate;
}
