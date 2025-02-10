package com.tuber.product.service.dataaccess.template.product.adapter;

import com.tuber.product.service.dataaccess.template.product.mapper.TemplateProductJpaMapper;
import com.tuber.product.service.dataaccess.template.product.repository.TemplateProductJpaRepository;
import com.tuber.product.service.domain.entity.TemplateProduct;
import com.tuber.product.service.domain.ports.output.repository.TemplateProductRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TemplateProductRepositoryImpl implements TemplateProductRepository {
    TemplateProductJpaMapper templateProductJpaMapper;
    TemplateProductJpaRepository templateProductJpaRepository;

    @Override
    public TemplateProduct save(TemplateProduct product) {
        return templateProductJpaMapper.templateProductJpaEntityToTemplateProductEntity(
                templateProductJpaRepository.save(
                        templateProductJpaMapper.productEntityToProductJpaEntity(product)
                )
        );
    }

    @Override
    public Boolean existsById(UUID id) {
        return templateProductJpaRepository.existsById(id);
    }

    @Override
    public TemplateProduct findById(UUID id) {
        return templateProductJpaMapper.templateProductJpaEntityToTemplateProductEntity(
                templateProductJpaRepository.findById(id).orElse(null)
        );
    }

    @Override
    public List<TemplateProduct> findAll() {
        return templateProductJpaRepository.findAll().stream()
                .map(templateProductJpaMapper::templateProductJpaEntityToTemplateProductEntity)
                .toList();
    }

    @Override
    public void delete(TemplateProduct product) {
        templateProductJpaRepository.delete(
                templateProductJpaMapper.productEntityToProductJpaEntity(product)
        );
    }
}
