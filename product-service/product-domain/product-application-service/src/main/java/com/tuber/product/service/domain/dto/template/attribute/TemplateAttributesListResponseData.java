package com.tuber.product.service.domain.dto.template.attribute;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TemplateAttributesListResponseData {
    List<TemplateAttributeResponseData> templateAttributes;
    Integer total;
}
