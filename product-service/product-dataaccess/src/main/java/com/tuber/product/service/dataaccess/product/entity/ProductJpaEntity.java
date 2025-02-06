package com.tuber.product.service.dataaccess.product.entity;

import com.tuber.product.service.dataaccess.category.entity.ProductCategoryJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductJpaEntity {
    @Id
    UUID id;
    String name;
    BigDecimal price;
    String description;
    String tags;
    Float rating;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    ProductCategoryJpaEntity category;
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<ProductAttributeJpaEntity> attributes;
    @Column(columnDefinition = "DATE")
    LocalDate createdAt;
    @Column(columnDefinition = "DATE")
    LocalDate updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductJpaEntity that = (ProductJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
