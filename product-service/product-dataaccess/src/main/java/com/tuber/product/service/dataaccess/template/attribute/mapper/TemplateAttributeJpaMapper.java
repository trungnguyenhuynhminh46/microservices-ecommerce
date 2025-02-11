package com.tuber.product.service.dataaccess.template.attribute.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.product.service.dataaccess.template.attribute.entity.TemplateAttributeJpaEntity;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class TemplateAttributeJpaMapper {
    public abstract TemplateAttribute templateAttributeJpaEntityToTemplateAttributeEntity(TemplateAttributeJpaEntity templateAttributeJpaEntity);

    public abstract TemplateAttributeJpaEntity templateAttributeEntityToTemplateAttributeJpaEntity(TemplateAttribute templateAttribute);

    public UUID map(UniqueUUID id) {
        return id.getValue();
    }

    public UniqueUUID map(UUID id) {
        return new UniqueUUID(id);
    }
}
