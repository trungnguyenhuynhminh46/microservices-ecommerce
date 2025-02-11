package com.tuber.product.service.domain.helper.template.product;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.product.ModifyProductCommand;
import com.tuber.product.service.domain.dto.product.ProductResponseData;
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
    public ApiResponse<ProductResponseData> updateProduct(ModifyProductCommand modifyProductCommand) {
        TemplateProduct savedTemplateProduct = commonHelper.verifyTemplateProductExist(modifyProductCommand.getId());
        templateAttributeRepository.deleteByTemplateProductId(modifyProductCommand.getId());
        TemplateProduct updatedTemplateProduct = templateProductMapper.updateTemplateProduct(modifyProductCommand, savedTemplateProduct);
        return ApiResponse.<ProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product updated successfully")
                .data(
                        templateProductMapper.templateProductToProductResponseData(
                                commonHelper.saveTemplateProduct(updatedTemplateProduct)
                        )
                ).build();
    }
}
