package com.tuber.product.service.domain.helper.category;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.category.ModifyProductCategoryCommand;
import com.tuber.product.service.domain.dto.category.ProductCategoryResponseData;
import com.tuber.product.service.domain.entity.ProductCategory;
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
public class UpdateCategoryHelper {
    ProductCategoryMapper productCategoryMapper;
    CommonHelper commonHelper;

    public ApiResponse<ProductCategoryResponseData> updateProductCategory(ModifyProductCategoryCommand modifyProductCategoryCommand) {
        ProductCategory savedProductCategory = commonHelper.verifyProductCategoryExist(modifyProductCategoryCommand.getCode());
        ProductCategory updatedProductCategory = productCategoryMapper.updateProductCategory(modifyProductCategoryCommand, savedProductCategory);
        return ApiResponse.<ProductCategoryResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product category updated successfully")
                .data(productCategoryMapper.productCategoryToProductCategoryResponseData(
                        commonHelper.saveProductCategory(updatedProductCategory)
                ))
                .build();
    }
}
