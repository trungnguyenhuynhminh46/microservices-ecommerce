package com.tuber.inventory.service.domain.dto.shared;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductIdWithSkuDTO {
    UUID productId;
    String sku;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductIdWithSkuDTO that = (ProductIdWithSkuDTO) o;
        return Objects.equals(productId, that.productId) && Objects.equals(sku, that.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, sku);
    }
}
