package com.tuber.product.service.domain.ports.output.repository;

import com.tuber.product.service.domain.entity.TemplateAttribute;

import java.util.UUID;

public interface TemplateAttributeRepository {
    TemplateAttribute save(TemplateAttribute attribute);
}
