package com.tuber.product.service.domain.helper;

import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.entity.TemplateProduct;
import com.tuber.product.service.domain.exception.ProductDomainException;
import com.tuber.product.service.domain.ports.output.repository.ProductCategoryRepository;
import com.tuber.product.service.domain.ports.output.repository.ProductRepository;
import com.tuber.product.service.domain.ports.output.repository.TemplateAttributeRepository;
import com.tuber.product.service.domain.ports.output.repository.TemplateProductRepository;
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
    TemplateProductRepository templateProductRepository;
    TemplateAttributeRepository templateAttributeRepository;

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

    public Product saveProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        if (savedProduct == null) {
            log.error(String.format("Failed to save product %s", product.getName()));
            throw new ProductDomainException(ProductResponseCode.PRODUCT_SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), product.getName());
        }
        return savedProduct;
    }

    public Product verifyProductExist(UUID productId) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            log.error(String.format("Product with id %s not found", productId));
            throw new ProductDomainException(ProductResponseCode.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND.value(), productId.toString());
        }
        return product;
    }

    public Product deleteProduct(UUID productId) {
        Product product = this.verifyProductExist(productId);
        productRepository.delete(product);
        return product;
    }

    public TemplateProduct saveTemplateProduct(TemplateProduct product) {
        TemplateProduct savedTemplateProduct = templateProductRepository.save(product);
        if (savedTemplateProduct == null) {
            log.error(String.format("Failed to save template product %s", product.getName()));
            throw new ProductDomainException(ProductResponseCode.TEMPLATE_PRODUCT_SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), product.getName());
        }
        return savedTemplateProduct;
    }

    public TemplateProduct verifyTemplateProductExist(UUID templateProductId) {
        TemplateProduct templateProduct = templateProductRepository.findById(templateProductId);
        if (templateProduct == null) {
            log.error(String.format("Template product with id %s not found", templateProductId));
            throw new ProductDomainException(ProductResponseCode.TEMPLATE_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND.value(), templateProductId.toString());
        }
        return templateProduct;
    }

    public TemplateProduct deleteTemplateProduct(UUID templateProductId) {
        TemplateProduct templateProduct = this.verifyTemplateProductExist(templateProductId);
        templateProductRepository.delete(templateProduct);
        return templateProduct;
    }

    public TemplateAttribute saveTemplateAttribute(TemplateAttribute templateAttribute) {
        TemplateAttribute savedTemplateAttribute = templateAttributeRepository.save(templateAttribute);
        if (savedTemplateAttribute == null) {
            log.error(String.format("Failed to save template attribute %s", templateAttribute.getName()));
            throw new ProductDomainException(ProductResponseCode.TEMPLATE_ATTRIBUTE_SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), templateAttribute.getName());
        }
        return null;
    }

    public TemplateAttribute verifyTemplateAttributeExist(UUID templateAttributeId) {
        TemplateAttribute templateAttribute = templateAttributeRepository.findById(templateAttributeId);
        if (templateAttribute == null) {
            log.error(String.format("Template attribute with id %s not found", templateAttributeId));
            throw new ProductDomainException(ProductResponseCode.TEMPLATE_ATTRIBUTE_NOT_FOUND, HttpStatus.NOT_FOUND.value(), templateAttributeId.toString());
        }
        return templateAttribute;
    }

    public TemplateAttribute deleteTemplateAttribute(UUID templateAttributeId) {
        TemplateAttribute templateAttribute = this.verifyTemplateAttributeExist(templateAttributeId);
        templateAttributeRepository.delete(templateAttribute);
        return templateAttribute;
    }
}
