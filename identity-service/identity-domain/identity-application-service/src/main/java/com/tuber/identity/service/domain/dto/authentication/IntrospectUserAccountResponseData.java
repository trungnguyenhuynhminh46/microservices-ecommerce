package com.tuber.identity.service.domain.dto.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IntrospectUserAccountResponseData {
    String issuer;
    String userId;
    Date expirationTime;
    boolean active;
}
