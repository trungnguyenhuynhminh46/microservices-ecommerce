package com.tuber.product.service.dataaccess.template.attribute.repository;

import com.tuber.product.service.dataaccess.template.attribute.entity.TemplateAttributeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TemplateAttributeJpaRepository extends JpaRepository<TemplateAttributeJpaEntity, UUID> {
}
