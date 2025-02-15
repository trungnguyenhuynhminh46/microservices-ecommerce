package com.tuber.product.service.domain.event;

import com.tuber.domain.event.DomainEvent;
import com.tuber.product.service.domain.entity.TemplateAttribute;

public class TemplateAttributeCreatedEvent implements DomainEvent<TemplateAttribute> {
    private final TemplateAttribute templateAttribute;

    public TemplateAttributeCreatedEvent(TemplateAttribute templateAttribute) {
        this.templateAttribute = templateAttribute;
    }

    public TemplateAttribute getTemplateAttribute() {
        return templateAttribute;
    }
}
