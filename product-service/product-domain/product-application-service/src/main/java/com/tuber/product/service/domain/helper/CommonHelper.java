package com.tuber.product.service.domain.helper;

import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.exception.ProductDomainException;
import com.tuber.product.service.domain.ports.output.repository.ProductCategoryRepository;
import com.tuber.product.service.domain.ports.output.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonHelper {
    ProductCategoryRepository productCategoryRepository;
    ProductRepository productRepository;

    public ProductCategory verifyProductCategoryExist(String code) {
        ProductCategory productCategory = productCategoryRepository.findByCode(code);
        if (productCategory == null) {
            log.error(String.format("Product category with code %s not found", code));
            throw new ProductDomainException(ProductResponseCode.PRODUCT_CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND.value(), code);
        }
        return productCategory;
    }

    public ProductCategory verifyProductCategoryExist(UUID categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId);
        if (productCategory == null) {
            log.error(String.format("Product category with id %s not found", categoryId));
            throw new ProductDomainException(ProductResponseCode.PRODUCT_CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND.value(), categoryId.toString());
        }
        return productCategory;
    }

    public void verifyProductCategoryNotExist(String code) {
        if (productCategoryRepository.existsByCode(code)) {
            log.error(String.format("Product category with code %s existed", code));
            throw new ProductDomainException(ProductResponseCode.PRODUCT_CATEGORY_WITH_CODE_EXISTED, HttpStatus.INTERNAL_SERVER_ERROR.value(), code);
        }
    }

    public ProductCategory saveProductCategory(ProductCategory category) {
        ProductCategory savedProductCategory = productCategoryRepository.save(category);
        if (savedProductCategory == null) {
            log.error(String.format("Failed to save product category with code %s", category.getCode()));
            throw new ProductDomainException(ProductResponseCode.PRODUCT_CATEGORY_SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), category.getCode());
        }
        return savedProductCategory;
    }

    public ProductCategory deleteProductCategory(String code) {
        ProductCategory savedProductCategory = this.verifyProductCategoryExist(code);
        productCategoryRepository.delete(savedProductCategory);
        return savedProductCategory;
    }

    public Product saveProduct(Product initializedProduct) {
        Product savedProduct = productRepository.save(initializedProduct);
        if (savedProduct == null) {
            log.error(String.format("Failed to save product %s", initializedProduct.getName()));
            throw new ProductDomainException(ProductResponseCode.PRODUCT_SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), initializedProduct.getName());
        }
        return savedProduct;
    }
}
