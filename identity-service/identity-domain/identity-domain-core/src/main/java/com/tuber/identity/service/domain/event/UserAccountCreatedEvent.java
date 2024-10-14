package com.tuber.identity.service.domain.event;

import com.tuber.domain.event.DomainEvent;
import com.tuber.identity.service.domain.entity.UserAccount;

public class UserAccountCreatedEvent implements DomainEvent<UserAccount> {
    private final UserAccount userAccount;

    public UserAccountCreatedEvent(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
}
