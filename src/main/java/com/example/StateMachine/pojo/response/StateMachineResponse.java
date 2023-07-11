package com.example.StateMachine.pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StateMachineResponse {
    @JsonProperty("next")
    private String nextState;

    private String message;

    @JsonProperty("current")
    private String currentState;
}
