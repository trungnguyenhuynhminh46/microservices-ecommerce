package com.tuber.product.service.domain.dto.product;

import com.tuber.application.validators.ValidUUID;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetProductQuery {
    @NotNull(message = "Product id is mandatory")
    @ValidUUID
    UUID id;
}
