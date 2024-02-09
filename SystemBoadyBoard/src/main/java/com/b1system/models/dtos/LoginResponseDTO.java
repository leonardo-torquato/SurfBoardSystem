package com.b1system.models.dtos;

import com.b1system.models.entities.User;
import jakarta.validation.constraints.NotEmpty;

public class LoginResponseDTO {

    @NotEmpty
    private User user;

    @NotEmpty
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(User user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public String getJwt(){
        return this.jwt;
    }

    public void setJwt(String jwt){
        this.jwt = jwt;
    }

}