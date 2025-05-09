package com.tuber.product.service.dataaccess.template.attribute.entity;

import com.tuber.product.service.dataaccess.template.product.entity.TemplateProductJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "template_attribute")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TemplateAttributeJpaEntity {
    @Id
    UUID id;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "templateAttributes")
    Set<TemplateProductJpaEntity> templateProducts = new HashSet<>();
    String name;
    String defaultValue;
    String options;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateAttributeJpaEntity that = (TemplateAttributeJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
