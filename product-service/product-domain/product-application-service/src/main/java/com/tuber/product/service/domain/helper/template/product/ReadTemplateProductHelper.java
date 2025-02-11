package com.tuber.product.service.domain.helper.template.product;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.product.ProductResponseData;
import com.tuber.product.service.domain.dto.product.ProductsListResponseData;
import com.tuber.product.service.domain.entity.TemplateProduct;
import com.tuber.product.service.domain.helper.CommonHelper;
import com.tuber.product.service.domain.mapper.TemplateProductMapper;
import com.tuber.product.service.domain.ports.output.repository.TemplateProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReadTemplateProductHelper {
    TemplateProductMapper templateProductMapper;
    TemplateProductRepository templateProductRepository;
    CommonHelper commonHelper;

    public ApiResponse<ProductResponseData> getSingleTemplateProduct(UUID templateProductId) {
        TemplateProduct templateProduct = commonHelper.verifyTemplateProductExist(templateProductId);
        return ApiResponse.<ProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template product retrieved successfully")
                .data(templateProductMapper.templateProductToProductResponseData(templateProduct))
                .build();
    }

    public ApiResponse<ProductsListResponseData> getAllProducts() {
        List<TemplateProduct> productsList = templateProductRepository.findAll();
        return ApiResponse.<ProductsListResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template products retrieved successfully")
                .data(templateProductMapper.templateProductsListToProductsListResponseData(productsList))
                .build();
    }
}
