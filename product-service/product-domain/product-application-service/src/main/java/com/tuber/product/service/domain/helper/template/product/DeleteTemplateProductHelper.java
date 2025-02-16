package com.tuber.product.service.domain.helper.template.product;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.template.product.DeleteTemplateProductCommand;
import com.tuber.product.service.domain.dto.template.product.TemplateProductResponseData;
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

    public ApiResponse<TemplateProductResponseData> deleteTemplateProduct(DeleteTemplateProductCommand deleteProductCommand) {
        TemplateProduct deletedTemplateProduct = commonHelper.deleteTemplateProduct(deleteProductCommand.getId());
        return ApiResponse.<TemplateProductResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Template product deleted successfully")
                .data(templateProductMapper.templateProductToTemplateProductResponseData(deletedTemplateProduct))
                .build();
    }
}
