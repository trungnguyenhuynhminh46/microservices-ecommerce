package com.tuber.product.service.domain.helper.category;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.ProductDomainService;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.category.CreateProductCategoryCommand;
import com.tuber.product.service.domain.dto.category.ProductCategoryResponseData;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.event.ProductCategoryCreatedEvent;
import com.tuber.product.service.domain.helper.CommonHelper;
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
    CommonHelper commonHelper;

    public ApiResponse<ProductCategoryResponseData> persistProductCategory(CreateProductCategoryCommand createProductCategoryCommand) {
        ProductCategory category = productCategoryMapper.createProductCategoryCommandToProductCategory(createProductCategoryCommand);
        ProductCategoryCreatedEvent productCategoryCreatedEvent = productDomainService.validateAndInitializeProductCategory(category);

        ProductCategory initializedCategory = productCategoryCreatedEvent.getProductCategory();
        commonHelper.verifyProductCategoryNotExist(initializedCategory.getCode());
        ProductCategoryResponseData createUserAccountResponseData =
                productCategoryMapper.productCategoryToProductCategoryResponseData(
                        commonHelper.saveProductCategory(initializedCategory)
                );
        return ApiResponse.<ProductCategoryResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product category created successfully")
                .data(createUserAccountResponseData)
                .build();
    }
}
