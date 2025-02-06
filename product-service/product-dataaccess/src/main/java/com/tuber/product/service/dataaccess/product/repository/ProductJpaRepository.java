package com.tuber.product.service.dataaccess.product.repository;

import com.tuber.product.service.dataaccess.product.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, UUID> {
}
