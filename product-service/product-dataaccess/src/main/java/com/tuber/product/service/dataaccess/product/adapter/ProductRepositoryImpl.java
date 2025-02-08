package com.tuber.product.service.dataaccess.product.adapter;

import com.tuber.product.service.dataaccess.product.mapper.ProductJpaMapper;
import com.tuber.product.service.dataaccess.product.repository.ProductJpaRepository;
import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.ports.output.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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

    @Override
    public Boolean existsById(UUID id) {
        return productJpaRepository.existsById(id);
    }

    @Override
    public Product findById(UUID id) {
        return productJpaMapper.productJpaEntityToProductEntity(
                productJpaRepository.findById(id).orElse(null)
        );
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(productJpaMapper::productJpaEntityToProductEntity)
                .toList();
    }

    @Override
    public void delete(Product product) {
        productJpaRepository.delete(
                productJpaMapper.productEntityToProductJpaEntity(product)
        );
    }
}
