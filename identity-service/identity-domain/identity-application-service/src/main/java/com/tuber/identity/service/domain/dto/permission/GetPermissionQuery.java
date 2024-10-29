package com.tuber.identity.service.domain.dto.permission;

import com.tuber.domain.valueobject.enums.UserPermission;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetPermissionQuery {
    @NotNull
    UserPermission name;
}
