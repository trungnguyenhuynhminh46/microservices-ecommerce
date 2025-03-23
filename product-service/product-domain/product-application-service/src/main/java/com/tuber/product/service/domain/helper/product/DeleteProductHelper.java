package com.tuber.product.service.domain.helper.product;

import com.tuber.application.dto.product.ProductResponseData;
import com.tuber.application.handler.ApiResponse;
import com.tuber.domain.constant.response.code.ProductResponseCode;
import com.tuber.product.service.domain.dto.product.DeleteProductCommand;
import com.tuber.product.service.domain.entity.Product;
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
public class DeleteProductHelper {
    CommonProductServiceHelper commonProductServiceHelper;
    ProductMapper productMapper;

    public ApiResponse<ProductResponseData> deleteProduct(DeleteProductCommand deleteProductCommand) {
        Product deletedProduct = commonProductServiceHelper.deleteProduct(deleteProductCommand.getId());
        return ApiResponse.<ProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product deleted successfully")
                .data(productMapper.productToProductResponseData(deletedProduct))
                .build();
    }
}
