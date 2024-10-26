package com.tuber.identity.service.domain.ports.output.repository;

import com.tuber.identity.service.domain.entity.Permission;

import java.util.Optional;
import java.util.Set;

public interface PermissionRepository {
    Permission save(Permission permission);
    Optional<Permission> findByName(String name);
    Set<Permission> findAll();
}
