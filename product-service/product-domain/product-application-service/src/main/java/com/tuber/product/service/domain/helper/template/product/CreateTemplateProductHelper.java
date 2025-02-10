package com.tuber.product.service.domain.helper.template.product;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.ProductDomainService;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.product.CreateProductCommand;
import com.tuber.product.service.domain.dto.product.ProductResponseData;
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

    public ApiResponse<ProductResponseData> persistTemplateProduct(CreateProductCommand createTemplateProductCommand) {
        TemplateProduct templateProduct = templateProductMapper.createProductCommandToTemplateProduct(createTemplateProductCommand);
        TemplateProductCreatedEvent templateProductCreatedEvent = productDomainService.validateAndInitializeTemplateProduct(templateProduct);

        TemplateProduct initializedTemplateProduct = templateProductCreatedEvent.getTemplateProduct();
        if (initializedTemplateProduct.getCategoryId() != null) {
            commonHelper.verifyProductCategoryExist(initializedTemplateProduct.getCategoryId());
        }
        ProductResponseData createTemplateProductResponseData =
                templateProductMapper.templateProductToProductResponseData(
                        commonHelper.saveTemplateProduct(initializedTemplateProduct)
                );
        return ApiResponse.<ProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template product created successfully")
                .data(createTemplateProductResponseData)
                .build();
    }
}
