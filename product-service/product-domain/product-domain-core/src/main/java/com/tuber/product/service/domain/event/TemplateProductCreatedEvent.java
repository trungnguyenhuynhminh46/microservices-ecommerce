package com.tuber.product.service.domain.event;

import com.tuber.domain.event.DomainEvent;
import com.tuber.product.service.domain.entity.TemplateProduct;

public class TemplateProductCreatedEvent implements DomainEvent<TemplateProduct> {
    private final TemplateProduct templateProduct;

    public TemplateProductCreatedEvent(TemplateProduct templateProduct) {
        this.templateProduct = templateProduct;
    }

    public TemplateProduct getTemplateProduct() {
        return templateProduct;
    }
}
