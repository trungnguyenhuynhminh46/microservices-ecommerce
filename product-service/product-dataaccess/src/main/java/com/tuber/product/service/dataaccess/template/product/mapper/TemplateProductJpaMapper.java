package com.tuber.product.service.dataaccess.template.product.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.domain.valueobject.valueobject.Money;
import com.tuber.product.service.dataaccess.template.attribute.entity.TemplateAttributeJpaEntity;
import com.tuber.product.service.dataaccess.template.attribute.mapper.TemplateAttributeJpaMapper;
import com.tuber.product.service.dataaccess.template.product.entity.TemplateProductJpaEntity;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.entity.TemplateProduct;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class TemplateProductJpaMapper {
    @Autowired
    TemplateAttributeJpaMapper templateAttributeJpaMapper;
    public abstract TemplateProduct templateProductJpaEntityToTemplateProductEntity(TemplateProductJpaEntity productJpaEntity);
    public abstract TemplateProductJpaEntity productEntityToProductJpaEntity(TemplateProduct product);
    @AfterMapping
    protected void afterMapping(@MappingTarget TemplateProductJpaEntity templateProductJpaEntity) {
        templateProductJpaEntity.getTemplateAttributes().forEach(templateAttributeJpaEntity -> templateAttributeJpaEntity.setTemplateProduct(templateProductJpaEntity));
    }
    protected UUID map (UniqueUUID id) {
        return id.getValue();
    }
    protected UniqueUUID map (UUID id) {
        return new UniqueUUID(id);
    }
    protected Money map(BigDecimal amount) {
        return new Money(amount);
    }
    protected BigDecimal map(Money money) {
        return money.getAmount();
    }
    protected TemplateAttribute map(TemplateAttributeJpaEntity productAttributeJpaEntity) {
        return templateAttributeJpaMapper.templateAttributeJpaEntityToTemplateAttributeEntity(productAttributeJpaEntity);
    }
    protected TemplateAttributeJpaEntity map(TemplateAttribute productAttribute) {
        return templateAttributeJpaMapper.templateAttributeEntityToTemplateAttributeJpaEntity(productAttribute);
    }
}
