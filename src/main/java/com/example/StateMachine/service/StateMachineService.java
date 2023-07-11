package com.example.StateMachine.service;

import com.example.StateMachine.configuration.UserStateMachineConfiguration;
import com.example.StateMachine.enums.UserRegistrationEvents;
import com.example.StateMachine.enums.UserRegistrationStates;
import com.example.StateMachine.pojo.response.StateMachineResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@Service
public class StateMachineService {
    @Autowired
    private UserStateMachineConfiguration userStateMachineConfiguration;

    public StateMachineResponse processStates(UserRegistrationStates initialState, UserRegistrationEvents event) {

        StateMachine<UserRegistrationStates, UserRegistrationEvents> stateMachineObj = null;
        StateMachineResponse stateMachineResponse = new StateMachineResponse();

        try {
            if (initialState == null || event == null) {
                return null;
            }
            stateMachineObj = userStateMachineConfiguration.stateMachine();
            stateMachineObj = startStateMachine(initialState, stateMachineObj);

            switch (event) {
                case OTP_VERIFICATION:
                case PASSWORD_LOGIN:
                case OTP_SENT:
                case SET_PASSWORD:
                case SET_ADDITIONAL_INFO:
                case SET_PHONE:
                case LOGIN_SUCCESS:
                case PAYMENT_PENDING:
                case SUCCESS:
                case LOCATION_MISMATCH:
                default:
                    stateMachineResponse = formatStateMachineResponse(event, event.getEvent(), stateMachineObj);
                    break;
            }
            destroyStateMachine(stateMachineObj);

        } catch (Exception e) {
            // Do Nothing
        }
        return stateMachineResponse;
    }

    private void destroyStateMachine(StateMachine<UserRegistrationStates, UserRegistrationEvents> stateMachineObj) {
        stateMachineObj.stopReactively();
    }

    private StateMachineResponse formatStateMachineResponse(UserRegistrationEvents event, String eventString, StateMachine<UserRegistrationStates, UserRegistrationEvents> stateMachineObj) {
        StateMachineResponse stateMachineResponse = new StateMachineResponse();

        stateMachineResponse.setCurrentState(getCurrentState(stateMachineObj).getState());
        stateMachineObj.getExtendedState().getVariables().put(eventString, true);
        stateMachineObj.sendEvent(event);
        stateMachineResponse.setMessage(getMessage(stateMachineObj));
        stateMachineResponse.setNextState(getCurrentState(stateMachineObj).getState());
        return stateMachineResponse;

    }

    private String getMessage(StateMachine<UserRegistrationStates, UserRegistrationEvents> stateMachineObj) {
        String message = null;
        UserRegistrationStates userRegistrationStates = stateMachineObj.getState().getId();
        switch (userRegistrationStates) {
            default:
                message = null;
                break;
        }
        return message;
    }

    private StateMachine<UserRegistrationStates, UserRegistrationEvents> startStateMachine(UserRegistrationStates initialState, StateMachine<UserRegistrationStates, UserRegistrationEvents> stateMachineObj) {
        stateMachineObj.stopReactively().block();
        stateMachineObj.getStateMachineAccessor()
                        .doWithAllRegions(function -> function.resetStateMachineReactively(new DefaultStateMachineContext<>(initialState, null, null, null)).block());
        stateMachineObj.startReactively().block();
        return stateMachineObj;
    }

    private UserRegistrationStates getCurrentState(StateMachine<UserRegistrationStates, UserRegistrationEvents> statesEventsStateMachine) {
        return statesEventsStateMachine.getState().getId();
    }
}