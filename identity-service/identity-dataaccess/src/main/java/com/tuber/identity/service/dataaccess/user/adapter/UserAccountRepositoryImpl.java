package com.tuber.identity.service.dataaccess.user.adapter;

import com.tuber.identity.service.dataaccess.CommonIdentityDataAccessHelper;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import com.tuber.identity.service.dataaccess.role.mapper.RoleDataAccessMapper;
import com.tuber.identity.service.dataaccess.user.entity.UserAccountJpaEntity;
import com.tuber.identity.service.domain.entity.Role;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import com.tuber.identity.service.dataaccess.user.mapper.UserAccountDataAccessMapper;
import com.tuber.identity.service.dataaccess.user.repository.UserAccountJpaRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountRepositoryImpl implements UserAccountRepository {
    UserAccountJpaRepository userAccountJpaRepository;
    UserAccountDataAccessMapper userAccountDataAccessMapper;
    CommonIdentityDataAccessHelper commonIdentityDataAccessHelper;
    RoleDataAccessMapper roleDataAccessMapper;

    @Override
    public UserAccount save(UserAccount userAccount) {
        return userAccountDataAccessMapper.userAccountJpaEntityToUserAccountEntity(
                userAccountJpaRepository.save(
                        userAccountDataAccessMapper.userAccountEntityToUserAccountJpaEntity(userAccount)
                )
        );
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userAccountJpaRepository.existsByUsername(username);
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return userAccountJpaRepository
                .findByUsername(username)
                .map(userAccountDataAccessMapper::userAccountJpaEntityToUserAccountEntity);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return userAccountJpaRepository
                .findByEmail(email)
                .map(userAccountDataAccessMapper::userAccountJpaEntityToUserAccountEntity);
    }

    @Override
    public Optional<UserAccount> findById(UUID id) {
        return userAccountJpaRepository
                .findById(id)
                .map(userAccountDataAccessMapper::userAccountJpaEntityToUserAccountEntity);
    }

    @Override
    public List<UserAccount> findAll() {
        return userAccountJpaRepository
                .findAll()
                .stream()
                .map(userAccountDataAccessMapper::userAccountJpaEntityToUserAccountEntity)
                .toList();
    }

    @Override
    @Transactional
    public Set<Role> assignRolesToUser(String username, Set<String> roleNames) {
        UserAccountJpaEntity userAccount = commonIdentityDataAccessHelper.verifyUserAccountWithUsernameExists(username);
        roleNames.forEach(
                roleName -> {
                    RoleJpaEntity role = commonIdentityDataAccessHelper.verifyRoleExists(roleName);
                    userAccount.getRoles().add(role);
                    userAccountJpaRepository.save(userAccount);
                }
        );

        return userAccount.getRoles().stream().map(roleDataAccessMapper::roleJpaEntityToRoleEntity).collect(Collectors.toSet());
    }
}
