package com.tuber.domain.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.valueobject.ProductAttributeOption;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductUtility {
    @Autowired
    ObjectMapper objectMapper;

    public String listOfAttributeOptionsToString(List<ProductAttributeOption> options) {
        try {
            return objectMapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductAttributeOption> optionsStringToListOfAttributeOptions(String options) {
        try {
            return objectMapper.readValue(options, objectMapper.getTypeFactory().constructCollectionType(List.class, ProductAttributeOption.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public String encodeAttributesToSku(Map<String, String> attributes) {
        // Example of sortedAttributesString: "color=red&size=large"
        String sortedAttributesString = attributes.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        return Base64.getEncoder().encodeToString(sortedAttributesString.getBytes());
    }

    public Map<String, String> decodeSkuToAttributes(String sku) {
        String decodedAttributesString = new String(Base64.getDecoder().decode(sku));
        return Arrays.stream(decodedAttributesString.split("&"))
                .map(attribute -> attribute.split("="))
                .collect(Collectors.toMap(attribute -> attribute[0], attribute -> attribute[1]));
    }
}
