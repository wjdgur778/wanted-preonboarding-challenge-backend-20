package com.example.demo.api.request;

import com.example.demo.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    public String userEmail;
    public String userPassword;
    public String userName;

    public User toUser(){
        return new User(userEmail,userPassword,userName);
    }
}
