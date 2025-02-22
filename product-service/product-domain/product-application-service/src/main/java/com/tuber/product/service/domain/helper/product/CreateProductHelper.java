package com.tuber.product.service.domain.helper.product;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.ProductDomainService;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.product.CreateProductCommand;
import com.tuber.product.service.domain.dto.product.ProductResponseData;
import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.event.ProductCreatedEvent;
import com.tuber.product.service.domain.helper.CommonProductServiceHelper;
import com.tuber.product.service.domain.mapper.ProductMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateProductHelper {
    ProductMapper productMapper;
    CommonProductServiceHelper commonProductServiceHelper;
    ProductDomainService productDomainService;

    public ApiResponse<ProductResponseData> persistProduct(CreateProductCommand createProductCommand) {
        Product product = productMapper.createProductCommandToProduct(createProductCommand);
        ProductCreatedEvent productCreatedEvent = productDomainService.validateAndInitializeProduct(product);

        Product initializedProduct = productCreatedEvent.getProduct();
        if (initializedProduct.getCategoryId() != null) {
            commonProductServiceHelper.verifyProductCategoryExist(initializedProduct.getCategoryId());
        }
        ProductResponseData createProductResponseData =
                productMapper.productToProductResponseData(
                        commonProductServiceHelper.saveProduct(initializedProduct)
                );
        return ApiResponse.<ProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product created successfully")
                .data(createProductResponseData)
                .build();
    }
}
