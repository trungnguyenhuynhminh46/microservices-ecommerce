package com.tuber.product.service.dataaccess.template.attribute.adapter;

import com.tuber.product.service.dataaccess.template.attribute.mapper.TemplateAttributeJpaMapper;
import com.tuber.product.service.dataaccess.template.attribute.repository.TemplateAttributeJpaRepository;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.ports.output.repository.TemplateAttributeRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TemplateAttributeRepositoryImpl implements TemplateAttributeRepository {
    TemplateAttributeJpaMapper templateAttributeJpaMapper;
    TemplateAttributeJpaRepository templateAttributeJpaRepository;

    @Override
    public TemplateAttribute save(TemplateAttribute attribute) {
        return templateAttributeJpaMapper.templateAttributeJpaEntityToTemplateAttributeEntity(
                templateAttributeJpaRepository.save(
                        templateAttributeJpaMapper.templateAttributeEntityToTemplateAttributeJpaEntity(attribute)
                )
        );
    }

    @Override
    public TemplateAttribute findById(UUID id) {
        return templateAttributeJpaMapper.templateAttributeJpaEntityToTemplateAttributeEntity(
                templateAttributeJpaRepository.findById(id).orElse(null)
        );
    }

    @Override
    public List<TemplateAttribute> findAll() {
        return templateAttributeJpaRepository.findAll().stream()
                .map(templateAttributeJpaMapper::templateAttributeJpaEntityToTemplateAttributeEntity)
                .toList();
    }

    @Override
    public void delete(TemplateAttribute attribute) {
        templateAttributeJpaRepository.delete(
                templateAttributeJpaMapper.templateAttributeEntityToTemplateAttributeJpaEntity(attribute)
        );
    }

    @Override
    public void deleteByTemplateProductsId(UUID id) {
        templateAttributeJpaRepository.deleteByTemplateProductsId(id);
    }

    @Override
    public Set<TemplateAttribute> findAllById(Set<UUID> ids) {
        return templateAttributeJpaRepository.findAllById(ids).stream()
                .map(templateAttributeJpaMapper::templateAttributeJpaEntityToTemplateAttributeEntity)
                .collect(Collectors.toSet());
    }
}
