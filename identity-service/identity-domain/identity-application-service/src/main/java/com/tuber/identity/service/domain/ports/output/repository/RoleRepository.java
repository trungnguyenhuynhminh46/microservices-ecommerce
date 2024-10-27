package com.tuber.identity.service.domain.ports.output.repository;

import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.identity.service.domain.entity.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findByName(String name);
    void assignPermissionsToRole(String roleName, UserPermission permission);
    Set<Role> findAll();
}
