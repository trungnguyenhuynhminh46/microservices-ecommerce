package com.tuber.order.service.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class ProductId {
    private UUID productId;
    private String sku;

    public ProductId(UUID productId, String sku) {
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
        ProductId productId1 = (ProductId) o;
        return Objects.equals(productId, productId1.productId) && Objects.equals(sku, productId1.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, sku);
    }
}
