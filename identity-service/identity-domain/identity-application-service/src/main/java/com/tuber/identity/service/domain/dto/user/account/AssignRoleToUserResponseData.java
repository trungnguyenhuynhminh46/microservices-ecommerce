package com.tuber.identity.service.domain.dto.user.account;

import com.tuber.identity.service.domain.entity.Role;
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
public class AssignRoleToUserResponseData {
    String username;
    Set<Role> roles;
}
