package com.tuber.product.service.domain.helper.template.attribute;

import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.constant.response.code.ProductResponseCode;
import com.tuber.product.service.domain.dto.template.attribute.ModifyTemplateAttributeCommand;
import com.tuber.product.service.domain.dto.template.attribute.TemplateAttributeResponseData;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.helper.CommonProductServiceHelper;
import com.tuber.product.service.domain.mapper.TemplateAttributeMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateTemplateAttributeHelper {
    TemplateAttributeMapper templateAttributeMapper;
    CommonProductServiceHelper commonProductServiceHelper;

    @Transactional
    public ApiResponse<TemplateAttributeResponseData> updateTemplateAttribute(ModifyTemplateAttributeCommand modifyTemplateAttributeCommand) {
        TemplateAttribute savedTemplateAttribute = commonProductServiceHelper.verifyTemplateAttributeExist(modifyTemplateAttributeCommand.getId());
        TemplateAttribute updatedTemplateAttribute = templateAttributeMapper.updateTemplateAttribute(modifyTemplateAttributeCommand, savedTemplateAttribute);

        return ApiResponse.<TemplateAttributeResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message(String.format("Template attribute with id %s updated successfully", modifyTemplateAttributeCommand.getId()))
                .data(
                        templateAttributeMapper.templateAttributeToTemplateAttributeResponseData(
                                commonProductServiceHelper.saveTemplateAttribute(updatedTemplateAttribute)
                        )
                ).build();
    }
}
