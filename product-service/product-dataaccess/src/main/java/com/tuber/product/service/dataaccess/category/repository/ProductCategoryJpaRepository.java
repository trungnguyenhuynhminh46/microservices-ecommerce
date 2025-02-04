package com.tuber.product.service.dataaccess.category.repository;

import com.tuber.product.service.dataaccess.category.entity.ProductCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategoryJpaEntity, String> {
    ProductCategoryJpaEntity findByCode(String code);
    Optional<ProductCategoryJpaEntity> findById(UUID id);
    Boolean existsByCode(String code);
}
