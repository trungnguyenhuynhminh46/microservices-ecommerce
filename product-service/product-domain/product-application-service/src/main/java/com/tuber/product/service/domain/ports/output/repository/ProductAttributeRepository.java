package com.tuber.product.service.domain.ports.output.repository;

import java.util.UUID;

public interface ProductAttributeRepository {
    void deleteByProductId(UUID productId);
}
