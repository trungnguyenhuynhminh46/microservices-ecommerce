package com.tuber.product.service.domain;

import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.entity.TemplateProduct;
import com.tuber.product.service.domain.event.ProductCategoryCreatedEvent;
import com.tuber.product.service.domain.event.ProductCreatedEvent;
import com.tuber.product.service.domain.event.TemplateAttributeCreatedEvent;
import com.tuber.product.service.domain.event.TemplateProductCreatedEvent;

public interface ProductDomainService {
    public ProductCategoryCreatedEvent validateAndInitializeProductCategory(ProductCategory productCategory);
    public ProductCreatedEvent validateAndInitializeProduct(Product product);
    public TemplateProductCreatedEvent validateAndInitializeTemplateProduct(TemplateProduct templateProduct);
    public TemplateAttributeCreatedEvent validateAndInitializeTemplateAttribute(TemplateAttribute templateAttribute);
}
