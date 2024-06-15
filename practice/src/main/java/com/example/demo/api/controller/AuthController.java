package com.example.demo.api.controller;

import com.example.demo.api.request.LoginRequestDto;
import com.example.demo.api.request.SignupRequestDto;
import com.example.demo.api.service.UserService;
import com.example.demo.common.exception.RestApiException;
import com.example.demo.common.model.Result;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Table(name = "user")
public class AuthController {
    /*
         final + RequiredArgsConstructor를 통해 의존성 주입 (생성자 주입)
     */
    private final UserService userService;


    /*
         spring boot version이 2.3이상부터 spring-boot-starter-web 의존성 내부에 있던 validation이
         사라졌기에 spring-boot-starter-validation을 추가했다.
         @Valid를 사용
     */
    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<Result> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) throws RestApiException {
        return ResponseEntity.status(200).body(Result.builder()
                .message("회원 가입 성공")
                .Data(userService.createUser(signupRequestDto.toUser()))
                .build()
        );
    }
    //로그인
    @PostMapping("/login")
    public ResponseEntity<Result> login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws RestApiException {
        return ResponseEntity.status(200).body(Result.builder()
                .message("회원 가입 성공")
                .Data(userService.login(loginRequestDto))
                .build()
        );
    }
    //중복 체크
    @GetMapping("/check-email")
    public ResponseEntity<Result> signup(@Valid @RequestParam("userEmail") String email){
        return ResponseEntity.status(200).body(Result.builder()
                .Data(true)
                .build()
        );
    }
    @GetMapping
    public ResponseEntity<Result> look(){
        return ResponseEntity.status(200).body(Result.builder()
                .Data(userService.getUser())
                .build()
        );
    }

}
