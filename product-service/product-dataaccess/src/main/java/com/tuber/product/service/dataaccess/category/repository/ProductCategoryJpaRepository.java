package com.tuber.product.service.dataaccess.category.repository;

import com.tuber.product.service.dataaccess.category.entity.ProductCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryJpaRepository extends JpaRepository<ProductCategoryJpaEntity, String> {
}
