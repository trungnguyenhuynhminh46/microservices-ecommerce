package com.tuber.application.mapper;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface JsonNullableMapper {
    default <T> JsonNullable<T> wrap(T entity) {
        return JsonNullable.of(entity);
    }

    default <T> T unwrap(JsonNullable<T> jsonNullable) {
        return jsonNullable == null ? null : jsonNullable.orElse(null);
    }

    @Named("isValidJsonNullable")
    @Condition
    default <T> boolean isValidJsonNullable(JsonNullable<T> param) {
        return param != null && param.isPresent();
    }
}
