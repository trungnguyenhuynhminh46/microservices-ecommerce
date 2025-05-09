package com.tuber.profile.service.domain.constant;

import com.tuber.domain.constant.response.code.ResponseCodeBase;

public class ProfileResponseCode extends ResponseCodeBase {
    protected final int defaultResponseCode = 1000;
    protected final String serviceName = "profile";
    public static final ProfileResponseCode DEFAULT_RESPONSE_CODE = new ProfileResponseCode(1000, "%s");
    public static final ProfileResponseCode USER_PROFILE_NOT_FOUND = new ProfileResponseCode(1001, "User profile with profile id {} not found");
    public static final ProfileResponseCode USER_PROFILE_SAVE_FAILED = new ProfileResponseCode(1002, "Cannot save the user profile of user with id %s");
    public static final ProfileResponseCode CITY_NOT_FOUND = new ProfileResponseCode(1003, "The city with name %s is not found");
    public static final ProfileResponseCode USER_ID_ALREADY_EXISTS = new ProfileResponseCode(1004, "The user profile with user id %s already exists");

    protected ProfileResponseCode(int code, String message) {
        this.code = formatErrorCode(code);
        this.message = message;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    protected String formatErrorCode(int code) {
        return String.format("%s_%d", this.serviceName, code);
    }
}
