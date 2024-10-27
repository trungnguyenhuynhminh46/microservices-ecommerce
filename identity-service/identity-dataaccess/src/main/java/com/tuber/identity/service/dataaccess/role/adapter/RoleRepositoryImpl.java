package com.tuber.identity.service.dataaccess.role.adapter;

import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import com.tuber.identity.service.dataaccess.permission.repository.PermissionJpaRepository;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import com.tuber.identity.service.dataaccess.role.mapper.RoleDataAccessMapper;
import com.tuber.identity.service.dataaccess.role.repository.RoleJpaRepository;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.ports.output.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleRepositoryImpl implements RoleRepository {
    RoleJpaRepository roleJpaRepository;
    PermissionJpaRepository permissionJpaRepository;
    RoleDataAccessMapper roleDataAccessMapper;

    @Override
    public Role save(Role role) {
        return roleDataAccessMapper.roleJpaEntityToRoleEntity(
                roleJpaRepository.save(
                        roleDataAccessMapper.roleEntityToRoleJpaEntity(role)
                )
        );
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleJpaRepository.findByName(name)
                .map(roleDataAccessMapper::roleJpaEntityToRoleEntity);
    }

    @Override
    @Transactional
    public void assignPermissionsToRole(String roleName, UserPermission permission) {
        Optional<RoleJpaEntity> roleJpaEntity = roleJpaRepository.findByName(roleName);
        Optional<PermissionJpaEntity> permissionJpaEntity = permissionJpaRepository.findByName(permission);
        if (roleJpaEntity.isPresent() && permissionJpaEntity.isPresent()) {
            roleJpaEntity.get().getPermissions().add(permissionJpaEntity.get());
            roleJpaRepository.save(roleJpaEntity.get());
        }
    }

    @Override
    public Set<Role> findAll() {
        return roleJpaRepository.findAll()
                .stream()
                .map(roleDataAccessMapper::roleJpaEntityToRoleEntity)
                .collect(java.util.stream.Collectors.toSet());
    }
}
