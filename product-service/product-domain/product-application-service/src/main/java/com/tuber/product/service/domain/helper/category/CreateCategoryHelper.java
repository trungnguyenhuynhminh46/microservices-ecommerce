package com.tuber.product.service.domain.helper.category;


import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.ProductDomainService;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.dto.category.CreateProductCategoryCommand;
import com.tuber.product.service.domain.dto.category.ProductCategoryResponseData;
import com.tuber.product.service.domain.entity.ProductCategory;
import com.tuber.product.service.domain.event.ProductCategoryCreatedEvent;
import com.tuber.product.service.domain.exception.ProductDomainException;
import com.tuber.product.service.domain.mapper.ProductCategoryMapper;
import com.tuber.product.service.domain.ports.output.repository.ProductCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateCategoryHelper {
    ProductDomainService productDomainService;
    ProductCategoryMapper productCategoryMapper;
    ProductCategoryRepository productCategoryRepository;

    private void verifyProductCategoryNotExist(String code) {
        if (productCategoryRepository.existsByCode(code)) {
            log.error(String.format("Product category with code %s existed", code));
            throw new ProductDomainException(ProductResponseCode.PRODUCT_CATEGORY_WITH_CODE_EXISTED, HttpStatus.INTERNAL_SERVER_ERROR.value(), code);
        }
    }

    private ProductCategory saveProductCategory(ProductCategory category) {
        ProductCategory savedProductCategory = productCategoryRepository.save(category);
        if (savedProductCategory == null) {
            log.error(String.format("Failed to save product category with code %s", category.getCode()));
            throw new ProductDomainException(ProductResponseCode.PRODUCT_CATEGORY_SAVE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), category.getCode());
        }
        return savedProductCategory;
    }

    public ApiResponse<ProductCategoryResponseData> persistProductCategory(CreateProductCategoryCommand createProductCategoryCommand) {
        ProductCategory category = productCategoryMapper.createProductCategoryCommandToProductCategory(createProductCategoryCommand);
        ProductCategoryCreatedEvent productCategoryCreatedEvent = productDomainService.validateAndInitializeProductCategory(category);

        // Do something with event if needed

        ProductCategory initializedCategory = productCategoryCreatedEvent.getProductCategory();
        this.verifyProductCategoryNotExist(initializedCategory.getCode());
        ProductCategoryResponseData createUserAccountResponseData =
                productCategoryMapper.productCategoryToProductCategoryResponseData(
                        this.saveProductCategory(initializedCategory)
                );
        return ApiResponse.<ProductCategoryResponseData>builder()
                .code(ProductResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Product category created successfully")
                .data(createUserAccountResponseData)
                .build();
    }
}
