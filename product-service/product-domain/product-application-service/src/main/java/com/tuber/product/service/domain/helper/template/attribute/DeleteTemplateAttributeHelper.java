package com.tuber.product.service.domain.helper.template.attribute;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.template.attribute.TemplateAttributeResponseData;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.helper.CommonHelper;
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
    CommonHelper commonHelper;
    TemplateAttributeMapper templateAttributeMapper;

    public ApiResponse<TemplateAttributeResponseData> deleteTemplateAttribute(UUID templateAttributeId) {
        TemplateAttribute deletedTemplateAttribute = commonHelper.deleteTemplateAttribute(templateAttributeId);
        return ApiResponse.<TemplateAttributeResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template attribute deleted successfully")
                .data(templateAttributeMapper.templateAttributeToTemplateAttributeResponseData(deletedTemplateAttribute))
                .build();
    }
}
