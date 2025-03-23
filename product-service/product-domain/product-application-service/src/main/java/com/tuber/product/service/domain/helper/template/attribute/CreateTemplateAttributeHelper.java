package com.tuber.product.service.domain.helper.template.attribute;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.ProductDomainService;
import com.tuber.domain.constant.response.code.ProductResponseCode;
import com.tuber.product.service.domain.dto.template.attribute.CreateTemplateAttributeCommand;
import com.tuber.product.service.domain.dto.template.attribute.TemplateAttributeResponseData;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.event.TemplateAttributeCreatedEvent;
import com.tuber.product.service.domain.helper.CommonProductServiceHelper;
import com.tuber.product.service.domain.mapper.TemplateAttributeMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateTemplateAttributeHelper {
    TemplateAttributeMapper templateAttributeMapper;
    CommonProductServiceHelper commonProductServiceHelper;
    ProductDomainService productDomainService;

    public ApiResponse<TemplateAttributeResponseData> createTemplateAttribute(CreateTemplateAttributeCommand createTemplateAttributeCommand) {
        TemplateAttribute templateAttribute = templateAttributeMapper.createTemplateAttributeCommand(createTemplateAttributeCommand);
        TemplateAttributeCreatedEvent templateAttributeCreatedEvent = productDomainService.validateAndInitializeTemplateAttribute(templateAttribute);

        TemplateAttribute initializedTemplateAttribute = templateAttributeCreatedEvent.getTemplateAttribute();
        TemplateAttributeResponseData createTemplateAttributeResponseData =
                templateAttributeMapper.templateAttributeToTemplateAttributeResponseData(
                        commonProductServiceHelper.saveTemplateAttribute(initializedTemplateAttribute)
                );
        return ApiResponse.<TemplateAttributeResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template attribute created successfully")
                .data(createTemplateAttributeResponseData)
                .build();
    }
}
