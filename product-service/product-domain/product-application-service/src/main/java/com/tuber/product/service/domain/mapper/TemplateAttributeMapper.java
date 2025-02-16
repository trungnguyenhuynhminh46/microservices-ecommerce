package com.tuber.product.service.domain.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.application.mapper.JsonNullableMapper;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.product.service.domain.dto.shared.TemplateAttributeOption;
import com.tuber.product.service.domain.dto.template.attribute.CreateTemplateAttributeCommand;
import com.tuber.product.service.domain.dto.template.attribute.ModifyTemplateAttributeCommand;
import com.tuber.product.service.domain.dto.template.attribute.TemplateAttributeResponseData;
import com.tuber.product.service.domain.dto.template.attribute.TemplateAttributesListResponseData;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import org.mapstruct.*;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper(uses = JsonNullableMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class TemplateAttributeMapper {
    @Autowired
    ObjectMapper objectMapper;

    public abstract TemplateAttribute createTemplateAttributeCommand(CreateTemplateAttributeCommand createTemplateAttributeCommand);

    public abstract TemplateAttributeResponseData templateAttributeToTemplateAttributeResponseData(TemplateAttribute templateAttribute);

    public TemplateAttribute updateTemplateAttribute(ModifyTemplateAttributeCommand modifyTemplateAttributeCommand, TemplateAttribute templateAttribute) {
        updateTemplateAttributeFields(modifyTemplateAttributeCommand, templateAttribute);
        templateAttribute.validateProperties();
        return templateAttribute;
    }

    @InheritConfiguration
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "defaultValue", conditionQualifiedByName = "isValidJsonNullable")
    @Mapping(target = "options", conditionQualifiedByName = "isValidJsonNullable")
    public abstract void updateTemplateAttributeFields(ModifyTemplateAttributeCommand modifyTemplateAttributeCommand, @MappingTarget TemplateAttribute templateAttribute);

    public String map(JsonNullable<List<TemplateAttributeOption>> options) {
        List<TemplateAttributeOption> optionsList = options.orElse(null);
        if (optionsList == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(optionsList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public TemplateAttributesListResponseData templateAttributesListToTemplateAttributesListResponseData(List<TemplateAttribute> templateAttributes) {
        List<TemplateAttributeResponseData> templateAttributeResponseDataList = templateAttributes.stream()
                .map(this::templateAttributeToTemplateAttributeResponseData)
                .toList();
        Integer total = templateAttributeResponseDataList.size();
        return TemplateAttributesListResponseData.builder()
                .templateAttributes(templateAttributeResponseDataList)
                .total(total)
                .build();
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

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }
}
