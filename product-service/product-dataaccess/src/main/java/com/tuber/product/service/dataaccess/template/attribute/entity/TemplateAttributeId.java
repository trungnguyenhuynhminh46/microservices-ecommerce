package com.tuber.product.service.dataaccess.template.attribute.entity;

import com.tuber.product.service.dataaccess.template.product.entity.TemplateProductJpaEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TemplateAttributeId {
    private Long id;
    private TemplateProductJpaEntity templateProduct;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateAttributeId that = (TemplateAttributeId) o;
        return Objects.equals(id, that.id) && Objects.equals(templateProduct, that.templateProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, templateProduct);
    }
}
