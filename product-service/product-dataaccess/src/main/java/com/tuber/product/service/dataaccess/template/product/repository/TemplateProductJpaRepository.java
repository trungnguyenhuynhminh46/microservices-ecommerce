package com.tuber.product.service.dataaccess.template.product.repository;

import com.tuber.product.service.dataaccess.template.product.entity.TemplateProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TemplateProductJpaRepository extends JpaRepository<TemplateProductJpaEntity, UUID> {
}
