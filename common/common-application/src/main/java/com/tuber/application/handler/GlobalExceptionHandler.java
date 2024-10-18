package com.tuber.application.handler;

import com.tuber.domain.exception.DomainException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import com.tuber.domain.constant.ResponseCode;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleDomainException(DomainException domainException) {
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
        log.error(exception.getMessage(), exception);
        return ApiResponse.builder()
                .code(ResponseCode.VALIDATION_ERROR.getCode())
                .messages(getErrorsMap(exception))
                .build();
    }

    private Map<String, List<String>> getErrorsMap(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .filter(fieldError -> fieldError.getDefaultMessage() != null)
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));
    }

    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleConstraintViolationException(ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.builder()
                .code(ResponseCode.VALIDATION_ERROR.getCode())
                .messages(extractMessagesFromException(exception))
                .build();
    }

    private Map<String, List<String>> extractMessagesFromException(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream()
                .reduce(new HashMap<String, List<String>>(), (map, violation) -> {
                    String field = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
                    String message = violation.getMessage();
                    map.putIfAbsent(field, Collections.emptyList());
                    map.computeIfPresent(field, (key, value) -> {
                        List<String> messages = new ArrayList<>(value);
                        messages.add(message);
                        return messages;
                    });
                    return map;
                }, (map1, map2) -> {
                    map1.putAll(map2);
                    return map1;
                });
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.builder()
                .code(ResponseCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(exception.getMessage())
                .build();
    }
}
