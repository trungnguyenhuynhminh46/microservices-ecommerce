package com.tuber.product.service.dataaccess.template.attribute.entity;

import com.tuber.product.service.dataaccess.template.product.entity.TemplateProductJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TemplateAttributeId.class)
@Table(name = "template_attribute")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TemplateAttributeJpaEntity {
    @Id
    private Long id;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "template_product_id")
    private TemplateProductJpaEntity templateProduct;

    String name;
    String defaultValue;
    String options;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateAttributeJpaEntity that = (TemplateAttributeJpaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(templateProduct, that.templateProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, templateProduct);
    }
}
