package com.tuber.product.service.dataaccess.product.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttributeId implements Serializable {
    private Long id;
    private ProductJpaEntity product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAttributeId that = (ProductAttributeId) o;
        return Objects.equals(id, that.id) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product);
    }
}
