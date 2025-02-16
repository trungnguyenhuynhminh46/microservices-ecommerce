package com.tuber.product.service.domain.dto.template.product;

import com.tuber.product.service.domain.dto.shared.TemplateAttributeDTO;
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
public class TemplateProductResponseData {
    UUID id;
    String name;
    BigDecimal price;
    String description;
    String tags;
    Float rating;
    UUID categoryId;
    List<TemplateAttributeDTO> attributes;
    String createdAt;
    String updatedAt;
}
