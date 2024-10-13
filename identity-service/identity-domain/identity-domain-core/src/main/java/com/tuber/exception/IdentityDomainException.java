package com.tuber.exception;

import com.tuber.domain.exception.DomainException;

public class IdentityDomainException extends DomainException {
    public IdentityDomainException(String message) {
        super(message);
    }

    public IdentityDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
