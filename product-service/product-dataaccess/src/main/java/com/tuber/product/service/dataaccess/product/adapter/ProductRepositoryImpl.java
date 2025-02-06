package com.tuber.product.service.dataaccess.product.adapter;

import com.tuber.product.service.dataaccess.product.mapper.ProductJpaMapper;
import com.tuber.product.service.dataaccess.product.repository.ProductJpaRepository;
import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.ports.output.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductRepositoryImpl implements ProductRepository {
    ProductJpaMapper productJpaMapper;
    ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        return productJpaMapper.productJpaEntityToProductEntity(
                productJpaRepository.save(
                        productJpaMapper.productEntityToProductJpaEntity(product)
                )
        );
    }
}
