package com.tuber.user.adapter;

import com.tuber.entity.UserAccount;
import com.tuber.ports.output.repository.UserAccountRepository;
import com.tuber.user.mapper.UserAccountDataAccessMapper;
import com.tuber.user.repository.UserAccountJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserAccountRepositoryImpl implements UserAccountRepository {
    private final UserAccountJpaRepository userAccountJpaRepository;
    private final UserAccountDataAccessMapper userAccountDataAccessMapper;

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
}
