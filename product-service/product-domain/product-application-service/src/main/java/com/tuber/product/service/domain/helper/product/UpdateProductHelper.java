package com.tuber.product.service.domain.helper.product;

import com.tuber.application.dto.product.ProductResponseData;
import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.constant.response.code.ProductResponseCode;
import com.tuber.product.service.domain.dto.product.ModifyProductCommand;
import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.helper.CommonProductServiceHelper;
import com.tuber.product.service.domain.mapper.ProductMapper;
import com.tuber.product.service.domain.ports.output.repository.ProductAttributeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateProductHelper {
    ProductMapper productMapper;
    CommonProductServiceHelper commonProductServiceHelper;
    ProductAttributeRepository productAttributeRepository;

    @Transactional
    public ApiResponse<ProductResponseData> updateProduct(ModifyProductCommand modifyProductCommand) {
        Product savedProduct = commonProductServiceHelper.verifyProductExist(modifyProductCommand.getId());
        productAttributeRepository.deleteByProductId(modifyProductCommand.getId());
        Product updatedProduct = productMapper.updateProduct(modifyProductCommand, savedProduct);
        return ApiResponse.<ProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product updated successfully")
                .data(productMapper.productToProductResponseData(
                        commonProductServiceHelper.saveProduct(updatedProduct)
                ))
                .build();
    }
}
