package com.tuber.application.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class ResponseBase<T> {
    String code;
    String message;
    T data;

    public static <T> ResponseBase<T> createResponse(String code, String message, T data) {
        return ResponseBase.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}
