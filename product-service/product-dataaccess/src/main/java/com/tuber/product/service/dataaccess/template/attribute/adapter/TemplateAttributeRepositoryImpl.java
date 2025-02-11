package com.tuber.product.service.dataaccess.template.attribute.adapter;

import com.tuber.product.service.dataaccess.template.attribute.repository.TemplateAttributeJpaRepository;
import com.tuber.product.service.domain.ports.output.repository.TemplateAttributeRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TemplateAttributeRepositoryImpl implements TemplateAttributeRepository {
    TemplateAttributeJpaRepository templateAttributeJpaRepository;

    @Override
    public void deleteByTemplateProductId(UUID templateProductId) {
        templateAttributeJpaRepository.deleteByTemplateProductId(templateProductId);
    }
}
