package com.tuber.product.service.dataaccess.attribute.adapter;

import com.tuber.product.service.dataaccess.attribute.repository.ProductAttributeJpaRepository;
import com.tuber.product.service.domain.ports.output.repository.ProductAttributeRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeImpl implements ProductAttributeRepository {
    ProductAttributeJpaRepository productAttributeJpaRepository;

    @Override
    public void deleteByProductId(UUID productId) {
        productAttributeJpaRepository.deleteByProductId(productId);
    }
}
