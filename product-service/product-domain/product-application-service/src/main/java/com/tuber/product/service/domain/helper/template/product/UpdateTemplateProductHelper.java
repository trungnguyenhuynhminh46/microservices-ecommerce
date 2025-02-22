package com.tuber.product.service.domain.helper.template.product;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.template.product.ModifyTemplateProductCommand;
import com.tuber.product.service.domain.dto.template.product.TemplateProductResponseData;
import com.tuber.product.service.domain.entity.TemplateAttribute;
import com.tuber.product.service.domain.entity.TemplateProduct;
import com.tuber.product.service.domain.helper.CommonProductServiceHelper;
import com.tuber.product.service.domain.mapper.TemplateProductMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateTemplateProductHelper {
    TemplateProductMapper templateProductMapper;
    CommonProductServiceHelper commonProductServiceHelper;

    @Transactional
    public ApiResponse<TemplateProductResponseData> updateTemplateProduct(ModifyTemplateProductCommand modifyProductCommand) {
        TemplateProduct savedTemplateProduct = commonProductServiceHelper.verifyTemplateProductExist(modifyProductCommand.getId());
        TemplateProduct updatedTemplateProduct = templateProductMapper.updateTemplateProduct(modifyProductCommand, savedTemplateProduct);

        if(modifyProductCommand.getAttributeIds() != null && modifyProductCommand.getAttributeIds().isPresent()) {
            if(modifyProductCommand.getAttributeIds().get() == null) {
                updatedTemplateProduct.setTemplateAttributes(Set.of());
            }
            if(modifyProductCommand.getAttributeIds().get() != null) {
                Set<TemplateAttribute> templateAttributes = commonProductServiceHelper.verifyTemplateAttributesByIdsExists(modifyProductCommand.getAttributeIds().get());
                updatedTemplateProduct.setTemplateAttributes(templateAttributes);
            }
        }

        return ApiResponse.<TemplateProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product updated successfully")
                .data(
                        templateProductMapper.templateProductToTemplateProductResponseData(
                                commonProductServiceHelper.saveTemplateProduct(updatedTemplateProduct)
                        )
                ).build();
    }
}
