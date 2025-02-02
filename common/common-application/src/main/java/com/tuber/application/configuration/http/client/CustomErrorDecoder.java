package com.tuber.application.configuration.http.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.domain.constant.ResponseCode;
import com.tuber.domain.exception.DomainException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomErrorDecoder implements ErrorDecoder {
    ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        String errorCode = "UNKNOWN_ERROR";
        String errorMessage = "An unexpected error occurred";

        try (InputStream bodyIs = response.body().asInputStream()) {
            JsonNode jsonNode = objectMapper.readTree(bodyIs);
            if (jsonNode.has("code")) {
                errorCode = jsonNode.get("code").asText();
            }
            if (jsonNode.has("message")) {
                errorMessage = jsonNode.get("message").asText();
            }
        } catch (IOException e) {
            return new FeignException.FeignClientException(response.status(), errorCode, response.request(), null, response.headers());
        }

        return new DomainException(new ResponseCode(errorCode, errorMessage), response.status());
    }
}
