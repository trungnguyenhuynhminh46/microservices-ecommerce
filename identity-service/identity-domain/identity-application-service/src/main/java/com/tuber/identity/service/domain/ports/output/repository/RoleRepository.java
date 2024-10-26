package com.tuber.identity.service.domain.ports.output.repository;

import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.entity.Role;

import java.util.Optional;

public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findByName(String name);
}
