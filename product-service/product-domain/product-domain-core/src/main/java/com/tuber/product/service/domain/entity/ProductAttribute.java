package com.tuber.product.service.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.id.LongId;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.exception.ProductDomainException;

import java.util.*;

public class ProductAttribute extends BaseEntity<LongId> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private UUID productId;
    private String name;
    private String options;

    private ProductAttribute(Builder builder) {
        super.setId(builder.id);
        setProductId(builder.productId);
        setName(builder.name);
        setOptions(builder.options);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getAttributeId() {
        return super.getId().getValue();
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public static final class Builder {
        private LongId id;
        private UUID productId;
        private String name;
        private String options;

        private Builder() {
        }

        public Builder id(Long val) {
            id = new LongId(val);
            return this;
        }

        public Builder id(LongId val) {
            id = val;
            return this;
        }

        public Builder productId(UUID val) {
            productId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder options(String val) {
            options = val;
            return this;
        }

        public ProductAttribute build() {
            return new ProductAttribute(this);
        }
    }

    public boolean isValidForInitialization() {
        return getName() != null && getOptions() != null && getId() == null && getProductId() == null;
    }

    public void validateOptionsString() {
        try {
            JsonNode rootNode = objectMapper.readTree(getOptions());

            if (!rootNode.isArray()) {
                throw new IllegalArgumentException("Product attribute options after being decoded must be an array");
            }

            for (JsonNode node : rootNode) {
                if (!node.has("name") || !node.has("changeAmount")) {
                    throw new IllegalArgumentException("Product attribute options must have 'name' and 'changeAmount' fields");
                }

                JsonNode nameNode = node.get("name");
                if (!nameNode.isTextual() || nameNode.asText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Product attribute options must have a non-empty 'name' field");
                }

                JsonNode changeAmountNode = node.get("changeAmount");
                if (!changeAmountNode.isNumber()) {
                    throw new IllegalArgumentException("Product attribute options must have a numeric 'changeAmount' field");
                }
            }
        }
        catch(JsonProcessingException e) {
            throw new IllegalArgumentException("Product attribute options is not a valid JSON string");
        }
    }

    public void validateForInitialization() {
        if (!isValidForInitialization()) {
            throw new ProductDomainException(ProductResponseCode.PRODUCT_ATTRIBUTE_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
        validateOptionsString();
    }
    public void initialize(Long attributeId, UUID productId) {
        setId(new LongId(attributeId));
        setProductId(productId);
    }
}
