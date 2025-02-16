package com.tuber.product.service.domain.helper.template.product;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.ProductDomainService;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.template.product.CreateTemplateProductCommand;
import com.tuber.product.service.domain.dto.template.product.TemplateProductResponseData;
import com.tuber.product.service.domain.entity.TemplateProduct;
import com.tuber.product.service.domain.event.TemplateProductCreatedEvent;
import com.tuber.product.service.domain.helper.CommonHelper;
import com.tuber.product.service.domain.mapper.TemplateProductMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateTemplateProductHelper {
    TemplateProductMapper templateProductMapper;
    CommonHelper commonHelper;
    ProductDomainService productDomainService;

    public ApiResponse<TemplateProductResponseData> persistTemplateProduct(CreateTemplateProductCommand createTemplateProductCommand) {
        TemplateProduct templateProduct = templateProductMapper.createTemplateProductCommandToTemplateProduct(createTemplateProductCommand);
        TemplateProductCreatedEvent templateProductCreatedEvent = productDomainService.validateAndInitializeTemplateProduct(templateProduct);

        TemplateProduct initializedTemplateProduct = templateProductCreatedEvent.getTemplateProduct();
        if (initializedTemplateProduct.getCategoryId() != null) {
            commonHelper.verifyProductCategoryExist(initializedTemplateProduct.getCategoryId());
        }
        TemplateProductResponseData createTemplateProductResponseData =
                templateProductMapper.templateProductToTemplateProductResponseData(
                        commonHelper.saveTemplateProduct(initializedTemplateProduct)
                );
        return ApiResponse.<TemplateProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template product created successfully")
                .data(createTemplateProductResponseData)
                .build();
    }
}
