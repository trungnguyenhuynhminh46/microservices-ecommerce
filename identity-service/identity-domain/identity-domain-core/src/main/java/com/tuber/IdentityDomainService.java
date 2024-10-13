package com.tuber;

import com.tuber.entity.UserAccount;
import com.tuber.event.UserAccountCreatedEvent;

public interface IdentityDomainService {
    public UserAccountCreatedEvent validateAndInitializeUserAccount(UserAccount userAccount);
}
