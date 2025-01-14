package com.tuber.profile.service.domain.constant;

import com.tuber.domain.constant.ResponseCodeBase;

public class ProfileResponseCode extends ResponseCodeBase {
    protected final int defaultResponseCode = 1000;
    protected final String serviceName = "identity";
    public static final ProfileResponseCode DEFAULT_RESPONSE_CODE = new ProfileResponseCode(1000, "%s");
    public static final ProfileResponseCode USER_PROFILE_NOT_FOUND = new ProfileResponseCode(1001, "User profile with profile id {} not found");
    public static final ProfileResponseCode USER_PROFILE_SAVE_FAILED = new ProfileResponseCode(1002, "Cannot save the user profile of user with id %s");
    public static final ProfileResponseCode CITY_NOT_FOUND = new ProfileResponseCode(1003, "The city with name %s is not found");

    protected ProfileResponseCode(int code, String message) {
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
