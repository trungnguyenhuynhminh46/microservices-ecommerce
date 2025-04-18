package com.tuber.product.service.domain.ports.output.repository;

import com.tuber.product.service.domain.entity.TemplateAttribute;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TemplateAttributeRepository {
    TemplateAttribute save(TemplateAttribute attribute);
    TemplateAttribute findById(UUID id);
    List<TemplateAttribute> findAll();
    void delete(TemplateAttribute attribute);
    void deleteByTemplateProductsId(UUID id);
    Set<TemplateAttribute> findAllById(Set<UUID> ids);
}
