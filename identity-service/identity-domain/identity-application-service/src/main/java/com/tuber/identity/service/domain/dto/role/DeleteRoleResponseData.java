package com.tuber.identity.service.domain.dto.role;

import com.tuber.identity.service.domain.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeleteRoleResponseData {
    Role deletedRole;
}
