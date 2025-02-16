package com.tuber.product.service.domain.helper.template.product;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.template.product.ModifyTemplateProductCommand;
import com.tuber.product.service.domain.dto.template.product.TemplateProductResponseData;
import com.tuber.product.service.domain.entity.TemplateProduct;
import com.tuber.product.service.domain.helper.CommonHelper;
import com.tuber.product.service.domain.mapper.TemplateProductMapper;
import com.tuber.product.service.domain.ports.output.repository.TemplateAttributeRepository;
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
public class UpdateTemplateProductHelper {
    TemplateProductMapper templateProductMapper;
    CommonHelper commonHelper;
    TemplateAttributeRepository templateAttributeRepository;

    @Transactional
    public ApiResponse<TemplateProductResponseData> updateTemplateProduct(ModifyTemplateProductCommand modifyProductCommand) {
        TemplateProduct savedTemplateProduct = commonHelper.verifyTemplateProductExist(modifyProductCommand.getId());
        templateAttributeRepository.deleteByTemplateProductsId(savedTemplateProduct.getId().getValue());
        TemplateProduct updatedTemplateProduct = templateProductMapper.updateTemplateProduct(modifyProductCommand, savedTemplateProduct);
        return ApiResponse.<TemplateProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product updated successfully")
                .data(
                        templateProductMapper.templateProductToTemplateProductResponseData(
                                commonHelper.saveTemplateProduct(updatedTemplateProduct)
                        )
                ).build();
    }
}
