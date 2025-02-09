package com.tuber.product.service.dataaccess.product.repository;

import com.tuber.product.service.dataaccess.product.entity.ProductAttributeId;
import com.tuber.product.service.dataaccess.product.entity.ProductAttributeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductAttributeJpaRepository extends JpaRepository<ProductAttributeJpaEntity, ProductAttributeId> {
    void deleteByProductId(UUID productId);
}
