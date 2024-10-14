package com.tuber.identity.service.domain.helper;

import com.tuber.identity.service.domain.IdentityDomainService;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;
import com.tuber.identity.service.domain.exception.IdentityDomainException;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserAccountHelper {
    private final UserDataMapper userDataMapper;
    private final IdentityDomainService identityDomainService;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    private void checkIfUsernameExist(String username) {
        Optional<UserAccount> userAccount = userAccountRepository.findByUsername(username);
        if (userAccount.isPresent()) {
            String message = String.format("User account with username %s existed", userAccount.get().getUsername());
            log.warn(message);
            throw new IdentityDomainException(message);
        }
    }

    private void checkIfEmailExist(String email) {
        Optional<UserAccount> userAccount = userAccountRepository.findByEmail(email);
        if (userAccount.isPresent()) {
            String message = String.format("User account with email %s existed", userAccount.get().getEmail());
            log.warn(message);
            throw new IdentityDomainException(message);
        }
    }

    private void encodePassword(UserAccount userAccount) {
        if(userAccount.isPasswordEncoded()) {
            return;
        }
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccount.setPasswordEncoded(true);
    }

    private void saveUserAccount(UserAccount userAccount) {
        UserAccount savedUserAccount = userAccountRepository.save(userAccount);
        if(savedUserAccount == null) {
            String message = String.format("Failed to save user account with username %s", userAccount.getUsername());
            log.error(message);
            throw new IdentityDomainException(message);
        }
        log.info("User account is saved with id: {}", savedUserAccount.getId().getValue());
    }

    @Transactional
    public UserAccountCreatedEvent persistUserAccount(CreateUserAccountCommand createUserAccountCommand) {
        UserAccount userAccount = userDataMapper.createUserAccountCommandToUserAccount(createUserAccountCommand);
        UserAccountCreatedEvent userAccountCreatedEvent = identityDomainService.validateAndInitializeUserAccount(userAccount);
        checkIfEmailExist(userAccount.getEmail());
        checkIfUsernameExist(userAccount.getUsername());
        encodePassword(userAccount);
        saveUserAccount(userAccount);
        log.info("User account is created with id: {}", userAccount.getId().getValue());
        return userAccountCreatedEvent;
    }
}
