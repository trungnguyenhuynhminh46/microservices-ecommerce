package com.tuber.product.service.dataaccess.attribute.entity;

import com.tuber.product.service.dataaccess.product.entity.ProductJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductAttributeId.class)
@Table(name = "p_attribute")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttributeJpaEntity {
    @Id
    private Long id;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;
    String name;
    String defaultValue;
    String options;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAttributeJpaEntity that = (ProductAttributeJpaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product);
    }
}
