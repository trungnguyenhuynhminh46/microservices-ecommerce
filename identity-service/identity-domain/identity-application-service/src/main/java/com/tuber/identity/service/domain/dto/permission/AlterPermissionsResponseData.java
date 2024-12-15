package com.tuber.identity.service.domain.dto.permission;

import com.tuber.identity.service.domain.entity.Permission;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AlterPermissionsResponseData {
    String role;
    Set<Permission> permissions;
}
