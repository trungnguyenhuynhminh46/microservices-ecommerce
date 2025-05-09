package com.tuber.identity.service.dataaccess.permission.adapter;

import com.tuber.identity.service.dataaccess.CommonIdentityDataAccessHelper;
import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import com.tuber.identity.service.dataaccess.permission.mapper.PermissionDataAccessMapper;
import com.tuber.identity.service.dataaccess.permission.repository.PermissionJpaRepository;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import com.tuber.identity.service.dataaccess.role.repository.RoleJpaRepository;
import com.tuber.identity.service.domain.entity.Permission;
import com.tuber.identity.service.domain.ports.output.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionRepositoryImpl implements PermissionRepository {
    PermissionJpaRepository permissionJpaRepository;
    PermissionDataAccessMapper permissionDataAccessMapper;
    CommonIdentityDataAccessHelper commonIdentityDataAccessHelper;
    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Permission save(Permission permission) {
        return permissionDataAccessMapper.permissionJpaEntityToPermissionEntity(
                permissionJpaRepository.save(
                        permissionDataAccessMapper.permissionEntityToPermissionJpaEntity(permission)
                )
        );
    }

    @Override
    public Optional<Permission> findByName(String name) {
        return permissionJpaRepository.findByName(name)
                .map(permissionDataAccessMapper::permissionJpaEntityToPermissionEntity);
    }

    @Override
    public Set<Permission> findAll() {
        return permissionJpaRepository.findAll()
                .stream()
                .map(permissionDataAccessMapper::permissionJpaEntityToPermissionEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Permission> findByRoleName(String roleName) {
        return permissionJpaRepository.findByRoleName(roleName)
                .stream()
                .map(permissionDataAccessMapper::permissionJpaEntityToPermissionEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Permission> findByUsername(String username) {
        return permissionJpaRepository.findByUsername(username)
                .stream()
                .map(permissionDataAccessMapper::permissionJpaEntityToPermissionEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Permission> assignPermissionsToRole(String roleName, Set<String> permissionNames) {
        RoleJpaEntity role = commonIdentityDataAccessHelper.verifyRoleExists(roleName);
        permissionNames.forEach(
                permissionName -> {
                    PermissionJpaEntity permission = commonIdentityDataAccessHelper.verifyPermissionExists(permissionName);
                    role.getPermissions().add(permission);
                    roleJpaRepository.save(role);
                }
        );

        return role.getPermissions().stream().map(permissionDataAccessMapper::permissionJpaEntityToPermissionEntity).collect(Collectors.toSet());
    }

    @Override
    public Set<Permission> removePermissionsFromRole(String roleName, Set<String> permissionNames) {
        RoleJpaEntity role = commonIdentityDataAccessHelper.verifyRoleExists(roleName);
        permissionNames.forEach(
                permissionName -> {
                    PermissionJpaEntity permission = commonIdentityDataAccessHelper.verifyPermissionExists(permissionName);
                    role.getPermissions().remove(permission);
                    roleJpaRepository.save(role);
                }
        );

        return role.getPermissions().stream().map(permissionDataAccessMapper::permissionJpaEntityToPermissionEntity).collect(Collectors.toSet());
    }

    @Override
    public Boolean existsByNameAndRoleName(String roleName, String permissionName) {
        return permissionJpaRepository.existsByNameAndRoleName(roleName, permissionName);
    }
}
