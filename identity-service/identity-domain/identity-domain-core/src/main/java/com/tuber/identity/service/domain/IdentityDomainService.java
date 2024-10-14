package com.tuber.identity.service.domain;

import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;

public interface IdentityDomainService {
    public UserAccountCreatedEvent validateAndInitializeUserAccount(UserAccount userAccount);
}
