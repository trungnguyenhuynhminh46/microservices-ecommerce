package com.tuber.identity.service.domain.ports.output.repository;

import com.tuber.identity.service.domain.entity.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findByName(String name);
    void assignPermissionsToRole(String roleName, String permission);
    Set<Role> findAll();
    Set<Role> findByUsername(String username);
    void delete(Role role);
}
