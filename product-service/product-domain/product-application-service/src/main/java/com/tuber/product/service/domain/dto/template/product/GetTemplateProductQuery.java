package com.tuber.product.service.domain.dto.template.product;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetTemplateProductQuery {
    @NotNull(message = "Product id is mandatory")
    UUID id;
}
