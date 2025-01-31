package com.tuber.product.service.dataaccess.category.adapter;

import com.tuber.product.service.dataaccess.category.mapper.ProductCategoryJpaMapper;
import com.tuber.product.service.dataaccess.category.repository.ProductCategoryJpaRepository;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.ports.output.repository.ProductCategoryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {
    ProductCategoryJpaMapper productCategoryJpaMapper;
    ProductCategoryJpaRepository productCategoryJpaRepository;

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryJpaMapper.productCategoryJpaEntityToProductCategoryEntity(
                productCategoryJpaRepository.save(
                        productCategoryJpaMapper.productCategoryEntityToProductCategoryJpaEntity(productCategory)
                )
        );
    }

    @Override
    public Boolean existsByCode(String code) {
        return productCategoryJpaRepository.existsById(code);
    }

    @Override
    public ProductCategory findByCode(String code) {
        return productCategoryJpaMapper.productCategoryJpaEntityToProductCategoryEntity(
                productCategoryJpaRepository.findByCode(code)
        );
    }

    @Override
    public Set<ProductCategory> findAll() {
        return productCategoryJpaRepository.findAll().stream()
                .map(productCategoryJpaMapper::productCategoryJpaEntityToProductCategoryEntity)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public void delete(ProductCategory productCategory) {
        productCategoryJpaRepository.delete(
                productCategoryJpaMapper.productCategoryEntityToProductCategoryJpaEntity(productCategory)
        );
    }
}
