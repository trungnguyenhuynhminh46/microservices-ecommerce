package com.tuber.identity.service.domain.dto.permission;

import com.tuber.identity.service.domain.entity.Permission;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetPermissionResponseData {
    Permission permission;
}
