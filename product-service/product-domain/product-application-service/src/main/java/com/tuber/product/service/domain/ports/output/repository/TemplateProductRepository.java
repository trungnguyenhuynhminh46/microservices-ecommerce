package com.tuber.product.service.domain.ports.output.repository;

import com.tuber.product.service.domain.entity.TemplateProduct;

import java.util.List;
import java.util.UUID;

public interface TemplateProductRepository {
    TemplateProduct save(TemplateProduct product);
    Boolean existsById(UUID id);
    TemplateProduct findById(UUID id);
    List<TemplateProduct> findAll();
    void delete(TemplateProduct product);
}
