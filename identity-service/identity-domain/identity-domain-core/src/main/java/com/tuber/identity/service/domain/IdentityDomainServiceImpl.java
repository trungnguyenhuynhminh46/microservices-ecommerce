package com.tuber.identity.service.domain;

import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdentityDomainServiceImpl implements IdentityDomainService {
    public UserAccountCreatedEvent validateAndInitializeUserAccount(UserAccount userAccount) {
        userAccount.validateUserAccount();
        userAccount.initializeUserAccount();
        log.info("User account with id: {} is initiated", userAccount.getId().getValue());
        return new UserAccountCreatedEvent(userAccount);
    }
}
