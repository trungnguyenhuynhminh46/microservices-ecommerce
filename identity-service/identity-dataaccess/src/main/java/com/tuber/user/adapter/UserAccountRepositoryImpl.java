package com.tuber.user.adapter;

import com.tuber.entity.UserAccount;
import com.tuber.ports.output.repository.UserAccountRepository;
import com.tuber.user.mapper.UserAccountDataAccessMapper;
import com.tuber.user.repository.UserAccountJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
}
