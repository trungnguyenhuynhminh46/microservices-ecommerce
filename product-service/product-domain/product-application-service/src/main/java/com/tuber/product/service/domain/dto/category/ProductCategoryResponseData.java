package com.tuber.product.service.domain.dto.category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCategoryResponseData {
    String code;
    String name;
    LocalDate createdAt;
    LocalDate updatedAt;
}
