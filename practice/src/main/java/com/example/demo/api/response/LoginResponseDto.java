package com.example.demo.api.response;

import com.example.demo.db.entity.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponseDto {
    public String accessToken;
    public String userEmail;
    public String userName;
}
