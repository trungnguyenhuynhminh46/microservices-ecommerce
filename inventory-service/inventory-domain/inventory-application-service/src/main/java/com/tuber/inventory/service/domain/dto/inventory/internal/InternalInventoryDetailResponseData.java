package com.tuber.inventory.service.domain.dto.inventory.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tuber.inventory.service.domain.dto.shared.ProductDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InternalInventoryDetailResponseData {
    UUID id;
    ProductDTO product;
    String sku;
    UUID warehouseId;
    Integer stockQuantity;
    LocalDate createdAt;
    LocalDate updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalInventoryDetailResponseData that = (InternalInventoryDetailResponseData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
