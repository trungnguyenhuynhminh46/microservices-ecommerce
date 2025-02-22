package com.tuber.product.service.domain.helper.template.attribute;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.template.attribute.GetTemplateAttributeQuery;
import com.tuber.product.service.domain.dto.template.attribute.TemplateAttributeResponseData;
import com.tuber.product.service.domain.dto.template.attribute.TemplateAttributesListResponseData;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.helper.CommonProductServiceHelper;
import com.tuber.product.service.domain.mapper.TemplateAttributeMapper;
import com.tuber.product.service.domain.ports.output.repository.TemplateAttributeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReadTemplateAttributeHelper {
    TemplateAttributeMapper templateAttributeMapper;
    TemplateAttributeRepository templateAttributeRepository;
    CommonProductServiceHelper commonProductServiceHelper;

    public ApiResponse<TemplateAttributeResponseData> getSingleTemplateAttribute(GetTemplateAttributeQuery getTemplateAttributeQuery) {
        TemplateAttribute templateAttribute = commonProductServiceHelper.verifyTemplateAttributeExist(getTemplateAttributeQuery.getId());
        return ApiResponse.<TemplateAttributeResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template attribute retrieved successfully")
                .data(templateAttributeMapper.templateAttributeToTemplateAttributeResponseData(templateAttribute))
                .build();
    }

    public ApiResponse<TemplateAttributesListResponseData> getAllTemplateAttributes() {
        List<TemplateAttribute> templateAttributesList = templateAttributeRepository.findAll();
        return ApiResponse.<TemplateAttributesListResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template attributes retrieved successfully")
                .data(templateAttributeMapper.templateAttributesListToTemplateAttributesListResponseData(templateAttributesList))
                .build();
    }
}
