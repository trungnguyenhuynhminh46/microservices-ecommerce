package com.tuber.product.service.dataaccess.template.attribute.mapper;

import com.tuber.domain.valueobject.id.LongId;
import com.tuber.product.service.dataaccess.template.attribute.entity.TemplateAttributeJpaEntity;
import com.tuber.product.service.dataaccess.template.product.entity.TemplateProductJpaEntity;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public  abstract class TemplateAttributeJpaMapper {
    @Mapping(target = "templateProductId", source = "templateProduct")
    public abstract TemplateAttribute templateAttributeJpaEntityToTemplateAttributeEntity(TemplateAttributeJpaEntity templateAttributeJpaEntity);
    @Mapping(target = "templateProduct", ignore = true)
    public abstract TemplateAttributeJpaEntity templateAttributeEntityToTemplateAttributeJpaEntity(TemplateAttribute templateAttribute);

    protected LongId map(Long id) {
        return new LongId(id);
    }

    protected Long map(LongId id) {
        return id.getValue();
    }

    protected UUID map(TemplateProductJpaEntity templateProductJpaEntity) {
        return templateProductJpaEntity.getId();
    }
}
