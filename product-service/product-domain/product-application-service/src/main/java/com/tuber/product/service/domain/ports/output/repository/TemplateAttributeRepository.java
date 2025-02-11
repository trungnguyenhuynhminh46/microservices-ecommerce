package com.tuber.product.service.domain.ports.output.repository;

import java.util.UUID;

public interface TemplateAttributeRepository {
    void deleteByTemplateProductId(UUID templateProductId);
}
