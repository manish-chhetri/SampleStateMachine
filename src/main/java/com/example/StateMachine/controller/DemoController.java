package com.example.StateMachine.controller;

import com.example.StateMachine.pojo.request.UserLoginRequest;
import com.example.StateMachine.pojo.response.UserResponse;
import com.example.StateMachine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/state-machine")
public class DemoController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody UserLoginRequest userLoginRequest) {

        UserResponse userResponse = userService.loginUser(userLoginRequest);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
