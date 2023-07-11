package com.example.StateMachine.enums;

public enum UserRegistrationStates implements States {
    USER_INIT("user_init"),
    FIND_USER("find_user"),
    SHOW_PASSWORD("show_password"),
    FORGOT_PASSWORD("forgot_password"),
    SHOW_OTP("show_otp"),
    OTP_VALIDATION("otp_validation"),
    NEW_PASSWORD("new_password"),
    NEW_PHONE("new_phone"),
    NEW_EMAIL("new_email"),
    NEW_ADDITIONAL_INFO("new_additional_info"),
    ACTIVATION_PENDING("activation_pending"),
    DASHBOARD("dashboard"),
    CHANGE_LOCATION("change_location");

    private String state;

    UserRegistrationStates(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }
}
