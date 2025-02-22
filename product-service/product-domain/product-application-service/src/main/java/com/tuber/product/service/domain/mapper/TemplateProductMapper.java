package com.tuber.product.service.domain.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.application.mapper.JsonNullableMapper;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.domain.valueobject.Money;
import com.tuber.product.service.domain.dto.shared.TemplateAttributeOption;
import com.tuber.product.service.domain.dto.template.product.CreateTemplateProductCommand;
import com.tuber.product.service.domain.dto.template.product.ModifyTemplateProductCommand;
import com.tuber.product.service.domain.dto.template.product.TemplateProductResponseData;
import com.tuber.product.service.domain.dto.template.product.TemplateProductsListResponseData;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.entity.TemplateProduct;
import org.mapstruct.*;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Mapper(uses = JsonNullableMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class TemplateProductMapper {
    @Autowired
    ObjectMapper objectMapper;

    @Mapping(target = "templateAttributes", ignore = true)
    public abstract TemplateProduct createTemplateProductCommandToTemplateProduct(CreateTemplateProductCommand createProductCommand);

    @Mapping(target = "attributes", source = "templateAttributes")
    public abstract TemplateProductResponseData templateProductToTemplateProductResponseData(TemplateProduct product);

    public TemplateProductsListResponseData templateProductsListToTemplateProductsListResponseData(List<TemplateProduct> products) {
        List<TemplateProductResponseData> productResponseDataList = products.stream()
                .map(this::templateProductToTemplateProductResponseData)
                .toList();
        Integer total = productResponseDataList.size();
        return TemplateProductsListResponseData.builder()
                .products(productResponseDataList)
                .total(total)
                .build();
    }

    public TemplateProduct updateTemplateProduct(ModifyTemplateProductCommand data, TemplateProduct templateProduct) {
        templateProduct.setUpdatedAt(LocalDate.now());
        this.updateTemplateProductFields(data, templateProduct);
        templateProduct.validateProperties();
        return templateProduct;
    }

    @InheritConfiguration
    @Mapping(target = "templateAttributes", ignore = true)
    protected abstract void updateTemplateProductFields(ModifyTemplateProductCommand data, @MappingTarget TemplateProduct templateProduct);

    protected List<TemplateAttribute> map(JsonNullable<List<UUID>> attributes) {
        List<UUID> attributesList = attributes.orElse(null);
        return attributesList == null ? List.of() : attributesList.stream()
                .map(attributeId -> TemplateAttribute.builder().id(attributeId).build())
                .toList();
    }

    protected String map(List<TemplateAttributeOption> options) {
        try {
            return objectMapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<TemplateAttributeOption> map(String options) {
        try {
            return objectMapper.readValue(options, objectMapper.getTypeFactory().constructCollectionType(List.class, TemplateAttributeOption.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected BigDecimal map(Money money) {
        return money.getAmount();
    }

    protected UUID map(UniqueUUID uniqueUUID) {
        return uniqueUUID.getValue();
    }

    protected UniqueUUID map(UUID uuid) {
        return new UniqueUUID(uuid);
    }

    protected Money map(BigDecimal bigDecimal) {
        return new Money(bigDecimal);
    }
}
