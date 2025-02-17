package com.tuber.product.service.domain;

import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.entity.TemplateProduct;
import com.tuber.product.service.domain.event.ProductCategoryCreatedEvent;
import com.tuber.product.service.domain.event.ProductCreatedEvent;
import com.tuber.product.service.domain.event.TemplateAttributeCreatedEvent;
import com.tuber.product.service.domain.event.TemplateProductCreatedEvent;

import java.util.List;
import java.util.Set;

public interface ProductDomainService {
    public ProductCategoryCreatedEvent validateAndInitializeProductCategory(ProductCategory productCategory);
    public ProductCreatedEvent validateAndInitializeProduct(Product product);
    public TemplateProductCreatedEvent validateAndInitializeTemplateProduct(TemplateProduct templateProduct, Set<TemplateAttribute> templateAttributes);
    public TemplateAttributeCreatedEvent validateAndInitializeTemplateAttribute(TemplateAttribute templateAttribute);
}
