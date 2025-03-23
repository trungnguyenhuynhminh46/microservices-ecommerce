package com.tuber.product.service.domain.helper.category;

import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.constant.response.code.ProductResponseCode;
import com.tuber.product.service.domain.dto.category.DeleteProductCategoryCommand;
import com.tuber.product.service.domain.dto.category.ProductCategoryResponseData;
import com.tuber.product.service.domain.entity.ProductCategory;
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
public class DeleteCategoryHelper {
    CommonProductServiceHelper commonProductServiceHelper;
    ProductCategoryMapper productCategoryMapper;

    public ApiResponse<ProductCategoryResponseData> deleteProductCategory(DeleteProductCategoryCommand deleteProductCategoryCommand) {
        ProductCategory deletedProductCategory = commonProductServiceHelper.deleteProductCategory(deleteProductCategoryCommand.getCode());
        return ApiResponse.<ProductCategoryResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product category updated successfully")
                .data(productCategoryMapper.productCategoryToProductCategoryResponseData(deletedProductCategory))
                .build();
    }
}
