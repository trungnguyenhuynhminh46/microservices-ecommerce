package com.tuber.product.service.domain.helper.category;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.category.GetProductCategoryQuery;
import com.tuber.product.service.domain.dto.category.ProductCategoryListResponseData;
import com.tuber.product.service.domain.dto.category.ProductCategoryResponseData;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.helper.CommonHelper;
import com.tuber.product.service.domain.mapper.ProductCategoryMapper;
import com.tuber.product.service.domain.ports.output.repository.ProductCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReadCategoryHelper {
    ProductCategoryMapper productCategoryMapper;
    ProductCategoryRepository productCategoryRepository;
    CommonHelper commonHelper;

    public ApiResponse<ProductCategoryResponseData> getProductCategory(GetProductCategoryQuery getProductCategoryQuery) {
        ProductCategory category = commonHelper.verifyProductCategoryExist(getProductCategoryQuery.getCode());
        return ApiResponse.<ProductCategoryResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product category retrieved successfully")
                .data(productCategoryMapper.productCategoryToProductCategoryResponseData(category))
                .build();
    }


    public ApiResponse<ProductCategoryListResponseData> getAllProductCategories() {
        Set<ProductCategory> productCategories = productCategoryRepository.findAll();
        return ApiResponse.<ProductCategoryListResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product categories retrieved successfully")
                .data(productCategoryMapper.productCategoryListToProductCategoryListResponseData(productCategories))
                .build();
    }
}
