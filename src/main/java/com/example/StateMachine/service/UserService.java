package com.example.StateMachine.service;

import com.example.StateMachine.enums.UserRegistrationEvents;
import com.example.StateMachine.enums.UserRegistrationStates;
import com.example.StateMachine.pojo.request.UserLoginRequest;
import com.example.StateMachine.pojo.response.StateMachineResponse;
import com.example.StateMachine.pojo.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    StateMachineService stateMachineService;

    public UserResponse loginUser(UserLoginRequest userLoginRequest) {

        UserResponse userResponse = new UserResponse();
        UserRegistrationEvents userRegistrationEvents = null;

        if ((Math.random()*10) > 5 ) {
            userRegistrationEvents = UserRegistrationEvents.OTP_VERIFICATION;
        } else {
            userRegistrationEvents = UserRegistrationEvents.OTP_SENT;
        }

        userResponse.setUserEmail(userLoginRequest.getUserEmail());

        StateMachineResponse stateMachineResponse = stateMachineService.processStates(UserRegistrationStates.FIND_USER, userRegistrationEvents);
        userResponse.setStateMachineResponse(stateMachineResponse);

        return userResponse;
    }
}
