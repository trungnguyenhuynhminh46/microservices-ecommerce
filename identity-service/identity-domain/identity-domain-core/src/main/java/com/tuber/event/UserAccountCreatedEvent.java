package com.tuber.event;

import com.tuber.domain.event.DomainEvent;
import com.tuber.entity.UserAccount;

public class UserAccountCreatedEvent implements DomainEvent<UserAccount> {
    private final UserAccount userAccount;

    public UserAccountCreatedEvent(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
}
