package com.example.StateMachine.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoginRequest {
    @JsonProperty("email")
    private String userEmail;

    @JsonProperty("password")
    private String userPassword;
}
