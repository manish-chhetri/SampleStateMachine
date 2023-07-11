package com.example.StateMachine.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    @JsonProperty("email")
    private String userEmail;

    @JsonProperty("password")
    private String userPassword;

    @JsonProperty("state")
    StateMachineResponse stateMachineResponse;
}
