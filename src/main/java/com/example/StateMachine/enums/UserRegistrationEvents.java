package com.example.StateMachine.enums;

public enum UserRegistrationEvents implements Events {
    OTP_VERIFICATION("otp_verification"),
    PASSWORD_LOGIN("password_login"),
    OTP_SENT("otp_sent"),
    SET_PASSWORD("set_password"),
    SET_PHONE("set_phone"),
    SET_EMAIL("set_email"),
    SET_ADDITIONAL_INFO("set_additional_info"),
    PAYMENT_PENDING("payment_pending"),
    LOGIN_SUCCESS("login_success"),
    SUCCESS("success"),
    LOCATION_MISMATCH("location_mismatch");
    
    private String event;


    UserRegistrationEvents(String event) {
        this.event = event;
    }

    public String getEvent() {
        return this.event;
    }
}
