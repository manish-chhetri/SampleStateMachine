package com.example.StateMachine.configuration;

import com.example.StateMachine.enums.UserRegistrationEvents;
import com.example.StateMachine.enums.UserRegistrationStates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class UserStateMachineConfiguration {
    public StateMachine<UserRegistrationStates, UserRegistrationEvents> stateMachine()
            throws Exception {
        Builder<UserRegistrationStates, UserRegistrationEvents> builder
                = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(false)
                .listener(listener());

        builder.configureStates()
                .withStates()
                .initial(UserRegistrationStates.USER_INIT)
                .states(EnumSet.allOf(UserRegistrationStates.class))
                .end(UserRegistrationStates.DASHBOARD);

        builder.configureTransitions()
                .withExternal()
                .source(UserRegistrationStates.USER_INIT).target(UserRegistrationStates.NEW_PASSWORD)
                .event(UserRegistrationEvents.SET_PASSWORD).guard(checkSetPassword()).and()
                .withExternal()
                .source(UserRegistrationStates.USER_INIT).target(UserRegistrationStates.NEW_EMAIL)
                .event(UserRegistrationEvents.SET_EMAIL).guard(checkSetEmail()).and()
                .withExternal()
                .source(UserRegistrationStates.FIND_USER).target(UserRegistrationStates.SHOW_OTP)
                .event(UserRegistrationEvents.OTP_VERIFICATION).guard(checkOtpVerification()).and()
                .withExternal()
                .source(UserRegistrationStates.FIND_USER).target(UserRegistrationStates.OTP_VALIDATION)
                .event(UserRegistrationEvents.OTP_SENT).guard(checkSentOtp());

        StateMachine<UserRegistrationStates, UserRegistrationEvents> stateMachine = builder.build();
        stateMachine.addStateListener(listener());

        return stateMachine;

    }

    public Guard<UserRegistrationStates, UserRegistrationEvents> checkSetEmail() {
        return context -> context.getExtendedState().get(UserRegistrationEvents.SET_EMAIL.getEvent(), Boolean.class);
    }

    public Guard<UserRegistrationStates, UserRegistrationEvents> checkSetPassword() {
        return context -> context.getExtendedState().get(UserRegistrationEvents.OTP_SENT.getEvent(), Boolean.class);
    }

    public Guard<UserRegistrationStates, UserRegistrationEvents> checkSentOtp() {
        return context -> context.getExtendedState().get(UserRegistrationEvents.OTP_SENT.getEvent(), Boolean.class);
    }

    public Guard<UserRegistrationStates, UserRegistrationEvents> checkOtpVerification() {
        return context -> context.getExtendedState().get(UserRegistrationEvents.OTP_VERIFICATION.getEvent(), Boolean.class);
    }

    @Bean
    public StateMachineListener<UserRegistrationStates, UserRegistrationEvents> listener() {
        return new StateMachineListenerAdapter<UserRegistrationStates, UserRegistrationEvents>() {
            @Override
            public void stateChanged(State<UserRegistrationStates, UserRegistrationEvents> from, State<UserRegistrationStates, UserRegistrationEvents> to) {
                System.out.println("State changed from " + from.getId() + " to " + to.getId());
            }
        };
    }
}
