package com.tuber.identity.service.dataaccess.role.adapter;

import com.tuber.identity.service.dataaccess.role.mapper.RoleDataAccessMapper;
import com.tuber.identity.service.dataaccess.role.repository.RoleJpaRepository;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.ports.output.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleRepositoryImpl implements RoleRepository {
    RoleJpaRepository roleJpaRepository;
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
}
