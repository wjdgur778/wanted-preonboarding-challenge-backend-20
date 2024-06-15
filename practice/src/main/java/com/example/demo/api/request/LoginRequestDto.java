package com.example.demo.api.request;

import com.example.demo.db.entity.User;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    public String userEmail;
    public String userPassword;
}
