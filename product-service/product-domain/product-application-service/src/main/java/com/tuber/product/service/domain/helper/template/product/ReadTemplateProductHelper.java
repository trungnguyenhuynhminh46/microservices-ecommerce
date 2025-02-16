package com.tuber.product.service.domain.helper.template.product;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.template.product.TemplateProductResponseData;
import com.tuber.product.service.domain.dto.template.product.TemplateProductsListResponseData;
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

    public ApiResponse<TemplateProductResponseData> getSingleTemplateProduct(UUID templateProductId) {
        TemplateProduct templateProduct = commonHelper.verifyTemplateProductExist(templateProductId);
        return ApiResponse.<TemplateProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template product retrieved successfully")
                .data(templateProductMapper.templateProductToTemplateProductResponseData(templateProduct))
                .build();
    }

    public ApiResponse<TemplateProductsListResponseData> getAllProducts() {
        List<TemplateProduct> productsList = templateProductRepository.findAll();
        return ApiResponse.<TemplateProductsListResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template products retrieved successfully")
                .data(templateProductMapper.templateProductsListToTemplateProductsListResponseData(productsList))
                .build();
    }
}
