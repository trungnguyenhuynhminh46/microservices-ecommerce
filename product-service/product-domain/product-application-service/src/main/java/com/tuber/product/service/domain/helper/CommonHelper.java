package com.tuber.product.service.domain.helper;

import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.exception.ProductDomainException;
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
public class CommonHelper {
    ProductCategoryRepository productCategoryRepository;
    public void verifyProductCategoryNotExist(String code) {
        if (productCategoryRepository.existsByCode(code)) {
            log.error(String.format("Product category with code %s existed", code));
            throw new ProductDomainException(ProductResponseCode.PRODUCT_CATEGORY_WITH_CODE_EXISTED, HttpStatus.INTERNAL_SERVER_ERROR.value(), code);
        }
    }
}
