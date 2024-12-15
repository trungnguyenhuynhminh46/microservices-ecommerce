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
    public static final IdentityResponseCode USER_ACCOUNT_WITH_USERNAME_NOT_FOUND =
            new IdentityResponseCode(1006, "User account with the username is not found");
    public static final IdentityResponseCode USER_ACCOUNT_WITH_EMAIL_NOT_FOUND =
            new IdentityResponseCode(1007, "User account with the username is not found");
    public static final IdentityResponseCode FAILED_AUTHENTICATION =
            new IdentityResponseCode(1008, "Username or password is wrong!");
    public static final IdentityResponseCode INVALID_JWT_TOKEN =
            new IdentityResponseCode(1009, "The jwt token is invalid");
    public static final IdentityResponseCode EXPIRED_JWT_TOKEN =
            new IdentityResponseCode(1010, "The jwt token is expired");
    public static final IdentityResponseCode INVALID_FORMAT_JWT_TOKEN =
            new IdentityResponseCode(1011, "The jwt token you provided has invalid format");
    public static final IdentityResponseCode INVALID_SIGNER_KEY =
            new IdentityResponseCode(1012, "The signer key has invalid format");
    public static final IdentityResponseCode REFRESH_TOKEN_SAVE_FAILED =
            new IdentityResponseCode(1013, "Failed to save the refresh token");
    public static final IdentityResponseCode LOGGED_OUT_ALREADY =
            new IdentityResponseCode(1014, "Your token is invalid or you logged out already. Please login again to continue!");
    public static final IdentityResponseCode ROLE_NOT_EXISTS =
            new IdentityResponseCode(1015, "The role does not exist");
    public static final IdentityResponseCode ROLE_EXISTED =
            new IdentityResponseCode(1016, "The role existed");
    public static final IdentityResponseCode PERMISSION_NOT_EXISTS =
            new IdentityResponseCode(1017, "The role does not exist");
    public static final IdentityResponseCode UNAUTHENTICATED =
            new IdentityResponseCode(1018, "You are not authenticated");
    public static final IdentityResponseCode PERMISSION_EXISTED =
            new IdentityResponseCode(1019, "The permission existed");
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
