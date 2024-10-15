package com.tuber.application.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.tuber.domain.constant.ResponseCode;

import java.util.List;
import java.util.Map;

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
