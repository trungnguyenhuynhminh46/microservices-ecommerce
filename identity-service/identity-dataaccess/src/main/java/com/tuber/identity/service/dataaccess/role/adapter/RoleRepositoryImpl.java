package com.tuber.identity.service.dataaccess.role.adapter;

import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import com.tuber.identity.service.dataaccess.permission.repository.PermissionJpaRepository;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import com.tuber.identity.service.dataaccess.role.mapper.RoleDataAccessMapper;
import com.tuber.identity.service.dataaccess.role.repository.RoleJpaRepository;
import com.tuber.identity.service.dataaccess.user.entity.UserAccountJpaEntity;
import com.tuber.identity.service.dataaccess.user.repository.UserAccountJpaRepository;
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
    UserAccountJpaRepository userAccountJpaRepository;
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
    public void assignPermissionsToRole(String roleName, String permission) {
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

    @Override
    public Set<Role> findByUsername(String username) {
        return roleJpaRepository.findByUsername(username)
                .stream()
                .map(roleDataAccessMapper::roleJpaEntityToRoleEntity)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public void delete(Role role) {
        RoleJpaEntity roleJpa = roleDataAccessMapper.roleEntityToRoleJpaEntity(role);
        Set<UserAccountJpaEntity> users = userAccountJpaRepository.findByRoleName(role.getName());
        for(UserAccountJpaEntity userJpa: users){
            userJpa.getRoles().remove(roleJpa);
            userAccountJpaRepository.save(userJpa);
        }
        roleJpaRepository.delete(roleJpa);
    }
}
