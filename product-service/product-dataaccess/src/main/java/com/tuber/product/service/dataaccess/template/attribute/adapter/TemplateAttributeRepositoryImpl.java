package com.tuber.product.service.dataaccess.template.attribute.adapter;

import com.tuber.product.service.dataaccess.template.attribute.repository.TemplateAttributeJpaRepository;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.ports.output.repository.TemplateAttributeRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TemplateAttributeRepositoryImpl implements TemplateAttributeRepository {
    @Override
    public TemplateAttribute save(TemplateAttribute attribute) {
        return null;
    }

    @Override
    public TemplateAttribute findById(UUID id) {
        return null;
    }

    @Override
    public List<TemplateAttribute> findAll() {
        return null;
    }
}
