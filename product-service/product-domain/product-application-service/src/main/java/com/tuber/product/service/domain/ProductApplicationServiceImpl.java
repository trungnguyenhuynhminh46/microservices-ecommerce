package com.tuber.product.service.domain;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.dto.category.*;
import com.tuber.product.service.domain.dto.product.*;
import com.tuber.product.service.domain.helper.category.CreateCategoryHelper;
import com.tuber.product.service.domain.helper.category.DeleteCategoryHelper;
import com.tuber.product.service.domain.helper.category.ReadCategoryHelper;
import com.tuber.product.service.domain.helper.category.UpdateCategoryHelper;
import com.tuber.product.service.domain.helper.product.CreateProductHelper;
import com.tuber.product.service.domain.helper.product.DeleteProductHelper;
import com.tuber.product.service.domain.helper.product.ReadProductHelper;
import com.tuber.product.service.domain.helper.product.UpdateProductHelper;
import com.tuber.product.service.domain.ports.input.service.ProductApplicationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductApplicationServiceImpl implements ProductApplicationService {
    CreateCategoryHelper createCategoryHelper;
    ReadCategoryHelper readCategoryHelper;
    UpdateCategoryHelper updateCategoryHelper;
    DeleteCategoryHelper deleteCategoryHelper;
    CreateProductHelper createProductHelper;
    ReadProductHelper readProductHelper;
    UpdateProductHelper updateProductHelper;
    DeleteProductHelper deleteProductHelper;

    @Override
    public ApiResponse<ProductCategoryResponseData> createProductCategory(CreateProductCategoryCommand createProductCategoryCommand) {
        return createCategoryHelper.persistProductCategory(createProductCategoryCommand);
    }

    @Override
    public ApiResponse<ProductCategoryResponseData> getProductCategory(GetProductCategoryQuery getProductCategoryQuery) {
        return readCategoryHelper.getProductCategory(getProductCategoryQuery);
    }

    @Override
    public ApiResponse<ProductCategoryListResponseData> getAllProductCategories() {
        return readCategoryHelper.getAllProductCategories();
    }

    @Override
    public ApiResponse<ProductCategoryResponseData> updateProductCategory(ModifyProductCategoryCommand modifyProductCategoryCommand) {
        return updateCategoryHelper.updateProductCategory(modifyProductCategoryCommand);
    }

    @Override
    public ApiResponse<ProductCategoryResponseData> deleteProductCategory(DeleteProductCategoryCommand deleteProductCategoryCommand) {
        return deleteCategoryHelper.deleteProductCategory(deleteProductCategoryCommand);
    }

    @Override
    public ApiResponse<ProductResponseData> createProduct(CreateProductCommand createProductCommand) {
        return createProductHelper.persistProduct(createProductCommand);
    }

    @Override
    public ApiResponse<ProductResponseData> getSingleProduct(GetProductQuery getProductQuery) {
        return readProductHelper.getSingleProduct(getProductQuery.getId());
    }

    @Override
    public ApiResponse<ProductsListResponseData> getAllProducts() {
        return readProductHelper.getAllProducts();
    }

    @Override
    public ApiResponse<ProductResponseData> updateProduct(ModifyProductCommand modifyProductCommand) {
        return updateProductHelper.updateProduct(modifyProductCommand);
    }

    @Override
    public ApiResponse<ProductResponseData> deleteProduct(DeleteProductCommand deleteProductCommand) {
        return deleteProductHelper.deleteProduct(deleteProductCommand);
    }
}
