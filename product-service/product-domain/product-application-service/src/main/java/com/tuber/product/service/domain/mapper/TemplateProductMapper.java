package com.tuber.product.service.domain.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.application.mapper.JsonNullableMapper;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.domain.valueobject.valueobject.Money;
import com.tuber.product.service.domain.dto.shared.ProductAttributeDTO;
import com.tuber.product.service.domain.dto.shared.ProductAttributeOption;
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

    @Mapping(target = "templateAttributes", source = "attributes")
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
        templateProduct.initializeTemplateAttributes();
        return templateProduct;
    }

    @InheritConfiguration
    @Mapping(target = "description", conditionQualifiedByName = "isValidJsonNullable")
    @Mapping(target = "tags", conditionQualifiedByName = "isValidJsonNullable")
    @Mapping(target = "categoryId", conditionQualifiedByName = "isValidJsonNullable")
    @Mapping(target = "templateAttributes", source = "attributes", conditionQualifiedByName = "isValidJsonNullable")
    protected abstract void updateTemplateProductFields(ModifyTemplateProductCommand data, @MappingTarget TemplateProduct templateProduct);

    protected List<TemplateAttribute> map(JsonNullable<List<ProductAttributeDTO>> attributes) {
        List<ProductAttributeDTO> attributesList = attributes.orElse(null);
        return attributesList == null ? List.of() : attributesList.stream()
                .map(attribute -> TemplateAttribute.builder()
                        .name(attribute.getName())
                        .defaultValue(attribute.getDefaultValue())
                        .options(map(attribute.getOptions()))
                        .build())
                .toList();
    }

    protected String map(List<ProductAttributeOption> options) {
        try {
            return objectMapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<ProductAttributeOption> map(String options) {
        try {
            return objectMapper.readValue(options, objectMapper.getTypeFactory().constructCollectionType(List.class, ProductAttributeOption.class));
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
