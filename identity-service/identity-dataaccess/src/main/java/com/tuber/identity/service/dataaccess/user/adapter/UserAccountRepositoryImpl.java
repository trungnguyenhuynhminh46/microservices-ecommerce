package com.tuber.identity.service.dataaccess.user.adapter;

import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import com.tuber.identity.service.dataaccess.user.mapper.UserAccountDataAccessMapper;
import com.tuber.identity.service.dataaccess.user.repository.UserAccountJpaRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountRepositoryImpl implements UserAccountRepository {
    UserAccountJpaRepository userAccountJpaRepository;
    UserAccountDataAccessMapper userAccountDataAccessMapper;

    @Override
    public UserAccount save(UserAccount userAccount) {
        return userAccountDataAccessMapper.userAccountJpaEntityToUserAccountEntity(
                userAccountJpaRepository.save(
                        userAccountDataAccessMapper.userAccountEntityToUserAccountJpaEntity(userAccount)
                )
        );
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
}
