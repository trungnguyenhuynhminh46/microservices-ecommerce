package com.tuber.product.service.domain.helper.template.product;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.product.DeleteProductCommand;
import com.tuber.product.service.domain.dto.product.ProductResponseData;
import com.tuber.product.service.domain.entity.TemplateProduct;
import com.tuber.product.service.domain.helper.CommonHelper;
import com.tuber.product.service.domain.mapper.TemplateProductMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeleteTemplateProductHelper {
    CommonHelper commonHelper;
    TemplateProductMapper templateProductMapper;

    public ApiResponse<ProductResponseData> deleteTemplateProduct(DeleteProductCommand deleteProductCommand) {
        TemplateProduct deletedTemplateProduct = commonHelper.deleteTemplateProduct(deleteProductCommand.getId());
        return ApiResponse.<ProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template product deleted successfully")
                .data(templateProductMapper.templateProductToProductResponseData(deletedTemplateProduct))
                .build();
    }
}
