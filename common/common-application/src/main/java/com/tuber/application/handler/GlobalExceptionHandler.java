package com.tuber.application.handler;

import com.tuber.domain.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import com.tuber.domain.constant.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleException(DomainException domainException) {
        log.error(domainException.getMessage(), domainException);
        return ApiResponse.builder()
                .code(domainException.getResponseCode().getCode())
                .message(domainException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        log.error(exception.getMessage(), exception);
        return ApiResponse.builder()
                .code(ResponseCode.VALIDATION_ERROR.getCode())
                .messages(errors)
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.builder()
                .code(ResponseCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message("Unexpected error!")
                .build();
    }
}
