package com.tuber.inventory.service.dataaccess.caching.product.repository;

import com.tuber.inventory.service.dataaccess.caching.product.entity.ProductHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductHashRepository extends CrudRepository<ProductHash, UUID> {
}
