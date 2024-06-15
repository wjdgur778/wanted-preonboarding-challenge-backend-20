package com.example.demo.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Email
    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    private String userEmail;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String userPassword;

    @NotBlank
    private String userName;

    public User(String userEmail,String userPassword,String userName){
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
    }
}
