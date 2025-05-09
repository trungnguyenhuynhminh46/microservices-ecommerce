package com.tuber.product.service.dataaccess.template.product.entity;

import com.tuber.product.service.dataaccess.template.attribute.entity.TemplateAttributeJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "template_product")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TemplateProductJpaEntity {
    @Id
    UUID id;
    String name;
    BigDecimal price;
    String description;
    String tags;
    Float rating;
    UUID categoryId;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "template_product_template_attribute",
            joinColumns = @JoinColumn(name = "template_product_id"),
            inverseJoinColumns = @JoinColumn(name = "template_attribute_id")
    )
    Set<TemplateAttributeJpaEntity> templateAttributes = new HashSet<>();
    @Column(columnDefinition = "DATE")
    LocalDate createdAt;
    @Column(columnDefinition = "DATE")
    LocalDate updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateProductJpaEntity that = (TemplateProductJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
