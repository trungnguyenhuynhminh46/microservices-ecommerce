package com.tuber.identity.service.domain.constant;

import com.tuber.domain.constant.ResponseCode;

public class IdentityResponseCode extends ResponseCode {
    public static final ResponseCode USER_ACCOUNT_SAVE_FAILED =
            new IdentityResponseCode(1001, "Cannot save the user account");
    public static final ResponseCode USER_ACCOUNT_WITH_USERNAME_EXISTED =
            new IdentityResponseCode(1002, "User with the username already existed");
    public static final ResponseCode USER_ACCOUNT_WITH_EMAIL_EXISTED =
            new IdentityResponseCode(1003, "User with the email already existed");
    public static final ResponseCode USER_ACCOUNT_IN_WRONG_STATE_FOR_INITIALIZATION =
            new IdentityResponseCode(1004, "User account is in wrong state for initialization");

    protected IdentityResponseCode(int code, String message) {
        super(code, message);
    }
}
