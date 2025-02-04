package com.tuber.product.service.domain.dto.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductResponseData {
    String name;
    BigDecimal price;
    String description;
    String categoryId;
    List<ProductAttribute> attributes;
    String tags;
}
