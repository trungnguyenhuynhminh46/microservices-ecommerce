package com.tuber.identity.service.domain.constant;
import com.tuber.domain.constant.ResponseCode;
import com.tuber.domain.constant.ResponseCodeBase;

public class IdentityResponseCode extends ResponseCodeBase {
    protected final String serviceName = "identity";
    public static final IdentityResponseCode SUCCESS_RESPONSE =
            new IdentityResponseCode(1000, "Your request is processed successfully!");
    public static final IdentityResponseCode UNCATEGORIZED_EXCEPTION =
            new IdentityResponseCode(9999, "Uncategorized error");
    public static final IdentityResponseCode VALIDATION_ERROR =
            new IdentityResponseCode(1001, "Validation error");
    public static final IdentityResponseCode USER_ACCOUNT_SAVE_FAILED =
            new IdentityResponseCode(1001, "Cannot save the user account");
    public static final IdentityResponseCode USER_ACCOUNT_WITH_USERNAME_EXISTED =
            new IdentityResponseCode(1002, "User with the username already existed");
    public static final IdentityResponseCode USER_ACCOUNT_WITH_EMAIL_EXISTED =
            new IdentityResponseCode(1003, "User with the email already existed");
    public static final IdentityResponseCode USER_ACCOUNT_IN_WRONG_STATE_FOR_INITIALIZATION =
            new IdentityResponseCode(1004, "User account is in wrong state for initialization");

    public static final IdentityResponseCode USER_ACCOUNT_WITH_ID_NOT_FOUND =
            new IdentityResponseCode(1005, "User account with the id not found");

    protected IdentityResponseCode(int code, String message) {
        this.code = formatErrorCode(code);
        this.message = message;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    protected String formatErrorCode(int code) {
        return String.format("%s_%d", this.serviceName, code);
    }
}
