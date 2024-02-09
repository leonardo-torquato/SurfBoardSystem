package com.b1system.controllers;

import com.b1system.models.createDtos.LoginDTO;
import com.b1system.models.createDtos.RegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b1system.models.entities.User;
import com.b1system.models.dtos.LoginResponseDTO;
import com.b1system.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping( "/register")
    public User registerUser(@Validated @RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body);
    }
    
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@Validated @RequestBody LoginDTO body){
        return authenticationService.loginUser(body);
    }
}   
