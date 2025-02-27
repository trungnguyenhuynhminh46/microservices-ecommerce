package com.tuber.application.helper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonProductHelper {
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
