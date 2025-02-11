package com.tuber.product.service.dataaccess.attribute.repository;

import com.tuber.product.service.dataaccess.attribute.entity.ProductAttributeId;
import com.tuber.product.service.dataaccess.attribute.entity.ProductAttributeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductAttributeJpaRepository extends JpaRepository<ProductAttributeJpaEntity, ProductAttributeId> {
    void deleteByProductId(UUID productId);
}
