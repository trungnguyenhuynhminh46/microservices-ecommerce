package com.tuber.product.service.domain.helper.category;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.ProductDomainService;
import com.tuber.domain.constant.response.code.ProductResponseCode;
import com.tuber.product.service.domain.dto.category.CreateProductCategoryCommand;
import com.tuber.product.service.domain.dto.category.ProductCategoryResponseData;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.event.ProductCategoryCreatedEvent;
import com.tuber.product.service.domain.helper.CommonProductServiceHelper;
import com.tuber.product.service.domain.mapper.ProductCategoryMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateCategoryHelper {
    ProductDomainService productDomainService;
    ProductCategoryMapper productCategoryMapper;
    CommonProductServiceHelper commonProductServiceHelper;

    public ApiResponse<ProductCategoryResponseData> persistProductCategory(CreateProductCategoryCommand createProductCategoryCommand) {
        ProductCategory category = productCategoryMapper.createProductCategoryCommandToProductCategory(createProductCategoryCommand);
        ProductCategoryCreatedEvent productCategoryCreatedEvent = productDomainService.validateAndInitializeProductCategory(category);

        ProductCategory initializedCategory = productCategoryCreatedEvent.getProductCategory();
        commonProductServiceHelper.verifyProductCategoryNotExist(initializedCategory.getCode());
        ProductCategoryResponseData createProductCategoryResponseData =
                productCategoryMapper.productCategoryToProductCategoryResponseData(
                        commonProductServiceHelper.saveProductCategory(initializedCategory)
                );
        return ApiResponse.<ProductCategoryResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product category created successfully")
                .data(createProductCategoryResponseData)
                .build();
    }
}
