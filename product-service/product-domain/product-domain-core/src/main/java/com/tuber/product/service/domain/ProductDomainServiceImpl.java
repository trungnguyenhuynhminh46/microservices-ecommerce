package com.tuber.product.service.domain;

import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.entity.TemplateProduct;
import com.tuber.product.service.domain.event.ProductCategoryCreatedEvent;
import com.tuber.product.service.domain.event.ProductCreatedEvent;
import com.tuber.product.service.domain.event.TemplateAttributeCreatedEvent;
import com.tuber.product.service.domain.event.TemplateProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
public class ProductDomainServiceImpl implements ProductDomainService {
    @Override
    public ProductCategoryCreatedEvent validateAndInitializeProductCategory(ProductCategory productCategory) {
        productCategory.validateForInitialization();
        productCategory.initialize();
        log.info("Product category with id: {} is initiated", productCategory.getCode());
        return new ProductCategoryCreatedEvent(productCategory);
    }

    @Override
    public ProductCreatedEvent validateAndInitializeProduct(Product product) {
        product.validateForInitialization();
        product.initialize();
        log.info("Validating product with id: {}", product.getId());
        return new ProductCreatedEvent(product);
    }

    @Override
    public TemplateProductCreatedEvent validateAndInitializeTemplateProduct(TemplateProduct templateProduct, Set<TemplateAttribute> templateAttributes) {
        templateProduct.validateForInitialization();
        templateProduct.initialize(templateAttributes);
        log.info("Validating template product with id: {}", templateProduct.getId());
        return new TemplateProductCreatedEvent(templateProduct);
    }

    @Override
    public TemplateAttributeCreatedEvent validateAndInitializeTemplateAttribute(TemplateAttribute templateAttribute) {
        templateAttribute.validateForInitialization();
        templateAttribute.initialize();
        log.info("Validating template attribute with id: {}", templateAttribute.getId());
        return new TemplateAttributeCreatedEvent(templateAttribute);
    }
}
