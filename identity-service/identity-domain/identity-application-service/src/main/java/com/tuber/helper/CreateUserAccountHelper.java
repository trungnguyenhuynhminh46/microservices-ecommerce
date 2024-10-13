package com.tuber.helper;

import com.tuber.dto.user.account.CreateUserAccountCommand;
import com.tuber.event.UserAccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserAccountHelper {
    @Transactional
    public UserAccountCreatedEvent persistUserAccount(CreateUserAccountCommand createUserAccountCommand) {
        // Execute business logic
        // Persist user account in database
        // Logging result
        // Return UserAccountCreatedEvent
        return null;
    }
}
