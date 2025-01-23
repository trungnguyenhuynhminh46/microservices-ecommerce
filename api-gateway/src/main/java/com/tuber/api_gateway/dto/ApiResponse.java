package com.tuber.api_gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class ApiResponse<T> {
    @Builder.Default
    String code = ResponseCode.SUCCESS_RESPONSE.getCode();
    String message;
    Object messages;
    T data;
}
