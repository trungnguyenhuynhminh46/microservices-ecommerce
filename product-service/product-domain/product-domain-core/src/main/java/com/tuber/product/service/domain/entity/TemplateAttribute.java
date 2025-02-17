package com.tuber.product.service.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.id.LongId;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.product.service.domain.constant.ProductResponseCode;
import com.tuber.product.service.domain.exception.ProductDomainException;

import java.util.UUID;

public class TemplateAttribute extends BaseEntity<UniqueUUID> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String name;
    private String defaultValue;
    private String options;

    public TemplateAttribute(UUID attributeId) {
        super.setId(new UniqueUUID(attributeId));
    }

    private TemplateAttribute(TemplateAttribute.Builder builder) {
        super.setId(builder.id);
        setName(builder.name);
        setDefaultValue(builder.defaultValue);
        setOptions(builder.options);
    }

    public static TemplateAttribute.Builder builder() {
        return new TemplateAttribute.Builder();
    }

    public UUID getAttributeId() {
        return super.getId().getValue();
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
        private UniqueUUID id;
        private String name;
        private String defaultValue;
        private String options;

        private Builder() {
        }

        public TemplateAttribute.Builder id(UUID val) {
            id = new UniqueUUID(val);
            return this;
        }

        public TemplateAttribute.Builder id(UniqueUUID val) {
            id = val;
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
        return isValid() && getId() == null;
    }

    public void validateOptionsAndDefaultValue() {
        try {
            JsonNode rootNode = objectMapper.readTree(getOptions());

            if (!rootNode.isArray()) {
                throw new IllegalArgumentException("Template attribute options after being decoded must be an array");
            }

            boolean defaultValueValid = false;

            for (JsonNode node : rootNode) {
                if (!node.has("name")) {
                    throw new IllegalArgumentException("Template attribute options must have 'name' field");
                }

                JsonNode nameNode = node.get("name");
                if (!nameNode.isTextual() || nameNode.asText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Template attribute options must have a non-empty 'name' field");
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
    public void initialize() {
        setId(new UniqueUUID(UUID.randomUUID()));
    }
}
