package com.tuber.helper;

import com.tuber.IdentityDomainService;
import com.tuber.dto.user.account.CreateUserAccountCommand;
import com.tuber.entity.UserAccount;
import com.tuber.event.UserAccountCreatedEvent;
import com.tuber.exception.IdentityDomainException;
import com.tuber.mapper.UserDataMapper;
import com.tuber.ports.output.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public UserAccountCreatedEvent persistUserAccount(CreateUserAccountCommand createUserAccountCommand) {
        UserAccount userAccount = userDataMapper.createUserAccountCommandToUserAccount(createUserAccountCommand);
        identityDomainService.validateAndInitializeUserAccount(userAccount);
        checkIfEmailExist(userAccount.getEmail());
        checkIfUsernameExist(userAccount.getUsername());
        // Encode password
        // Persist user account in database
        // Logging result
        // Return UserAccountCreatedEvent
        return null;
    }
}
