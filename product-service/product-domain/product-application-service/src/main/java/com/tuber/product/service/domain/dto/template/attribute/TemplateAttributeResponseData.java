package com.tuber.product.service.domain.dto.template.attribute;

import com.tuber.product.service.domain.dto.shared.TemplateAttributeOption;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TemplateAttributeResponseData {
    UUID id;
    String name;
    String description;
    List<TemplateAttributeOption> options;
}
