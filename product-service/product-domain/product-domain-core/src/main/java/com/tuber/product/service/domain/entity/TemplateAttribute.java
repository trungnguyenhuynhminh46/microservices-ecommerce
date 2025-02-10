package com.tuber.product.service.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.id.LongId;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.exception.ProductDomainException;

import java.util.UUID;

public class TemplateAttribute extends BaseEntity<LongId> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private UUID templateProductId;
    private String name;
    private String defaultValue;
    private String options;

    private TemplateAttribute(TemplateAttribute.Builder builder) {
        super.setId(builder.id);
        setTemplateProductId(builder.templateProductId);
        setName(builder.name);
        setDefaultValue(builder.defaultValue);
        setOptions(builder.options);
    }

    public static TemplateAttribute.Builder builder() {
        return new TemplateAttribute.Builder();
    }

    public Long getAttributeId() {
        return super.getId().getValue();
    }

    public UUID getTemplateProductId() {
        return templateProductId;
    }

    public void setTemplateProductId(UUID templateProductId) {
        this.templateProductId = templateProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public static final class Builder {
        private LongId id;
        private UUID templateProductId;
        private String name;
        private String defaultValue;
        private String options;

        private Builder() {
        }

        public TemplateAttribute.Builder id(Long val) {
            id = new LongId(val);
            return this;
        }

        public TemplateAttribute.Builder id(LongId val) {
            id = val;
            return this;
        }

        public TemplateAttribute.Builder templateProductId(UUID val) {
            templateProductId = val;
            return this;
        }

        public TemplateAttribute.Builder name(String val) {
            name = val;
            return this;
        }

        public TemplateAttribute.Builder defaultValue(String val) {
            defaultValue = val;
            return this;
        }

        public TemplateAttribute.Builder options(String val) {
            options = val;
            return this;
        }

        public TemplateAttribute build() {
            return new TemplateAttribute(this);
        }
    }

    public boolean isValid() {
        return getName() != null && getOptions() != null;
    }


    public boolean isValidForInitialization() {
        return isValid() && getId() == null && getTemplateProductId() == null;
    }

    public void validateOptionsAndDefaultValue() {
        try {
            JsonNode rootNode = objectMapper.readTree(getOptions());

            if (!rootNode.isArray()) {
                throw new IllegalArgumentException("Template attribute options after being decoded must be an array");
            }

            boolean defaultValueValid = false;

            for (JsonNode node : rootNode) {
                if (!node.has("name") || !node.has("changeAmount")) {
                    throw new IllegalArgumentException("Template attribute options must have 'name' and 'changeAmount' fields");
                }

                JsonNode nameNode = node.get("name");
                if (!nameNode.isTextual() || nameNode.asText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Template attribute options must have a non-empty 'name' field");
                }

                JsonNode changeAmountNode = node.get("changeAmount");
                if (!changeAmountNode.isNumber()) {
                    throw new IllegalArgumentException("Template attribute options must have a numeric 'changeAmount' field");
                }

                if (nameNode.asText().equals(getDefaultValue())) {
                    defaultValueValid = true;
                }
            }

            if (getDefaultValue() != null && !defaultValueValid) {
                throw new IllegalArgumentException(String.format("Template attribute default value '%s' is not in the options list", getDefaultValue()));
            }
        }
        catch(JsonProcessingException e) {
            throw new IllegalArgumentException("Template attribute options is not a valid JSON string");
        }
    }

    public void validateProperties() {
        if (!isValid()) {
            throw new ProductDomainException(ProductResponseCode.TEMPLATE_PRODUCT_ATTRIBUTE_INVALID, 406);
        }
        validateOptionsAndDefaultValue();
    }

    public void validateForInitialization() {
        if (!isValidForInitialization()) {
            throw new ProductDomainException(ProductResponseCode.TEMPLATE_PRODUCT_ATTRIBUTE_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
        validateOptionsAndDefaultValue();
    }
    public void initialize(Long attributeId, UUID productId) {
        setId(new LongId(attributeId));
        setTemplateProductId(productId);
    }
}
