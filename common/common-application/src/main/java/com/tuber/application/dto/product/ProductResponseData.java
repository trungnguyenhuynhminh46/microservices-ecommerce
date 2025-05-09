package com.tuber.application.dto.product;

import com.tuber.application.dto.product.ProductAttributeDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductResponseData {
    UUID id;
    String name;
    BigDecimal price;
    String description;
    String tags;
    Float rating;
    UUID categoryId;
    List<ProductAttributeDTO> attributes;
    String createdAt;
    String updatedAt;
}
