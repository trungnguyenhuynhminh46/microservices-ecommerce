package com.tuber.inventory.service.domain.dto.http.client.product;

import jakarta.validation.constraints.NotNull;
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
public class ProductAttributeDTO {
    @NotNull
    String name;
    String defaultValue;
    @NotNull
    List<ProductAttributeOption> options;
}
