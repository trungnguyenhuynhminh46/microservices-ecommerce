package com.tuber.product.service.domain.helper.template.attribute;

import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.constant.response.code.ProductResponseCode;
import com.tuber.product.service.domain.dto.template.attribute.TemplateAttributeResponseData;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.helper.CommonProductServiceHelper;
import com.tuber.product.service.domain.mapper.TemplateAttributeMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeleteTemplateAttributeHelper {
    CommonProductServiceHelper commonProductServiceHelper;
    TemplateAttributeMapper templateAttributeMapper;

    public ApiResponse<TemplateAttributeResponseData> deleteTemplateAttribute(UUID templateAttributeId) {
        TemplateAttribute deletedTemplateAttribute = commonProductServiceHelper.deleteTemplateAttribute(templateAttributeId);
        return ApiResponse.<TemplateAttributeResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template attribute deleted successfully")
                .data(templateAttributeMapper.templateAttributeToTemplateAttributeResponseData(deletedTemplateAttribute))
                .build();
    }
}
