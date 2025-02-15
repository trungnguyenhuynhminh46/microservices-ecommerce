package com.tuber.product.service.domain.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.product.service.domain.dto.shared.ProductAttributeOption;
import com.tuber.product.service.domain.dto.template.attribute.CreateTemplateAttributeCommand;
import com.tuber.product.service.domain.dto.template.attribute.TemplateAttributeResponseData;
import com.tuber.product.service.domain.dto.template.attribute.TemplateAttributesListResponseData;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class TemplateAttributeMapper {
    @Autowired
    ObjectMapper objectMapper;

    public abstract TemplateAttribute createTemplateAttributeCommand(CreateTemplateAttributeCommand createTemplateAttributeCommand);

    public abstract TemplateAttributeResponseData templateAttributeToTemplateAttributeResponseData(TemplateAttribute templateAttribute);

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

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }
}
