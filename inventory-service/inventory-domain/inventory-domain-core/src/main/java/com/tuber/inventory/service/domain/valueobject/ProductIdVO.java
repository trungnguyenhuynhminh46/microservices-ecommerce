package com.tuber.inventory.service.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class ProductIdVO {
    private final UUID productId;
    private final String sku;

    public ProductIdVO(UUID productId, String sku) {
        this.productId = productId;
        this.sku = sku;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getSku() {
        return sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductIdVO that = (ProductIdVO) o;
        return Objects.equals(productId, that.productId) && Objects.equals(sku, that.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, sku);
    }
}
