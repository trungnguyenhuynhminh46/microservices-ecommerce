package com.tuber.product.service.domain.helper.product;

import com.tuber.application.dto.product.GetProductDetailsQuery;
import com.tuber.application.dto.product.ProductResponseData;
import com.tuber.application.dto.product.ProductsListResponseData;
import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.constant.response.code.ProductResponseCode;
import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.helper.CommonProductServiceHelper;
import com.tuber.product.service.domain.mapper.ProductMapper;
import com.tuber.product.service.domain.ports.output.repository.ProductRepository;
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
public class ReadProductHelper {
    ProductMapper productMapper;
    ProductRepository productRepository;
    CommonProductServiceHelper commonProductServiceHelper;

    public ApiResponse<ProductResponseData> getSingleProduct(UUID productId) {
        Product product = commonProductServiceHelper.verifyProductExist(productId);
        return ApiResponse.<ProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product retrieved successfully")
                .data(productMapper.productToProductResponseData(product))
                .build();
    }

    public ApiResponse<ProductsListResponseData> getAllProducts() {
        List<Product> productsList = productRepository.findAll();
        return ApiResponse.<ProductsListResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Products retrieved successfully")
                .data(productMapper.productsListToProductsListResponseData(productsList))
                .build();
    }

    public ApiResponse<ProductsListResponseData> getProductDetails(GetProductDetailsQuery getProductDetailsQuery) {
        return ApiResponse.<ProductsListResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Products details retrieved successfully")
                .data(productMapper.setOfProductsToProductsListResponseData(
                        productRepository.findAllById(getProductDetailsQuery.getProductIds())
                ))
                .build();
    }
}
