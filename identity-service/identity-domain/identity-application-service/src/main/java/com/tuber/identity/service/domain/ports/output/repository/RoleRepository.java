package com.tuber.identity.service.domain.ports.output.repository;

import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.domain.valueobject.enums.UserRole;
import com.tuber.identity.service.domain.entity.Role;

import java.util.Optional;

public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findByName(UserRole name);
    void assignPermissionsToRole(UserRole roleName, UserPermission permission);
}
