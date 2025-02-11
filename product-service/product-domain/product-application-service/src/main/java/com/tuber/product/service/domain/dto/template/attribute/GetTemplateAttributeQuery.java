package com.tuber.product.service.domain.dto.template.attribute;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetTemplateAttributeQuery {
    @NotNull(message = "Template product id is mandatory")
    UUID id;
}
