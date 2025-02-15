package com.tuber.product.service.domain.constant;

import com.tuber.domain.constant.ResponseCodeBase;

public class ProductResponseCode extends ResponseCodeBase {
    protected final String serviceName = "product";
    public static final ProductResponseCode SUCCESS_RESPONSE =
            new ProductResponseCode(1000, "Your request is processed successfully!");
    public static final ProductResponseCode UNCATEGORIZED_EXCEPTION =
            new ProductResponseCode(9999, "Uncategorized error");
    public static final ProductResponseCode PRODUCT_IN_WRONG_STATE_FOR_INITIALIZATION =
            new ProductResponseCode(1001, "Product is in wrong state for initialization");
    public static final ProductResponseCode PRODUCT_CATEGORY_IN_WRONG_STATE_FOR_INITIALIZATION =
            new ProductResponseCode(1002, "Product category is in wrong state for initialization");
    public static final ProductResponseCode PRODUCT_ATTRIBUTE_IN_WRONG_STATE_FOR_INITIALIZATION =
            new ProductResponseCode(1003, "Product attribute is in wrong state for initialization");
    public static final ProductResponseCode PRODUCT_CATEGORY_WITH_CODE_EXISTED =
            new ProductResponseCode(1004, "Product category with code %s existed in the database");
    public static final ProductResponseCode PRODUCT_CATEGORY_SAVE_FAILED =
            new ProductResponseCode(1005, "Failed to save product category with code %s");
    public static final ProductResponseCode PRODUCT_CATEGORY_NOT_FOUND =
            new ProductResponseCode(1006, "Product category with code %s not found or has been deleted already");
    public static final ProductResponseCode PRODUCT_SAVE_FAILED =
            new ProductResponseCode(1007, "Failed to save product %s");
    public static final ProductResponseCode PRODUCT_NOT_FOUND =
            new ProductResponseCode(1008, "Product with id %s not found or has been deleted already");
    public static final ProductResponseCode PRODUCT_ATTRIBUTE_INVALID =
            new ProductResponseCode(1009, "Product attribute is invalid");
    public static final ProductResponseCode TEMPLATE_PRODUCT_IN_WRONG_STATE_FOR_INITIALIZATION =
            new ProductResponseCode(1010, "Template product is in wrong state for initialization");
    public static final ProductResponseCode TEMPLATE_PRODUCT_ATTRIBUTE_IN_WRONG_STATE_FOR_INITIALIZATION =
            new ProductResponseCode(1011, "Product attribute is in wrong state for initialization");
    public static final ProductResponseCode TEMPLATE_PRODUCT_SAVE_FAILED =
            new ProductResponseCode(1012, "Failed to save product %s");
    public static final ProductResponseCode TEMPLATE_PRODUCT_NOT_FOUND =
            new ProductResponseCode(1013, "Product with id %s not found or has been deleted already");
    public static final ProductResponseCode TEMPLATE_PRODUCT_ATTRIBUTE_INVALID =
            new ProductResponseCode(1014, "Product attribute is invalid");
    public static final ProductResponseCode TEMPLATE_ATTRIBUTE_SAVE_FAILED =
            new ProductResponseCode(1015, "Failed to save template attribute %s");
    public static final ProductResponseCode TEMPLATE_ATTRIBUTE_NOT_FOUND =
            new ProductResponseCode(1016, "Template attribute with id %s not found or has been deleted already");
    protected ProductResponseCode(int code, String message) {
        this.code = formatErrorCode(code);
        this.message = message;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    protected String formatErrorCode(int code) {
        return String.format("%s_%d", this.serviceName, code);
    }
}
