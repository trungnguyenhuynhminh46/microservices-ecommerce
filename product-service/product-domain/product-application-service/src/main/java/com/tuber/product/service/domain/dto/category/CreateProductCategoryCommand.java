package com.tuber.product.service.domain.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateProductCategoryCommand {
    @NotNull
    @NotBlank(message = "Category name is mandatory")
    String name;
    String description;
}
