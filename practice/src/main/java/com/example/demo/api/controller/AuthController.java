package com.example.demo.api.controller;

import com.example.demo.api.request.LoginRequestDto;
import com.example.demo.api.request.SignupRequestDto;
import com.example.demo.api.service.UserService;
import com.example.demo.common.exception.ErrorCode;
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
    public ResponseEntity<Result> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        //try catch를 이용하여 service에서 던지는 예외를 받아 처리한다.
        //Result라는 객체를 만들어서 로직이 성공한다면 요청한 데이터를 보내주고
        //예외가 있다면 예외처리된 값을 보내준다(error message & http status)
        //핸들러를 통하지 않고 controller에서 직관적으로 예외처리를 한다.

        //(수정)
        //코드 중복: 각 컨트롤러 메서드마다 try-catch 블록을 작성해야 하므로 코드 중복이 발생
        //유지보수 어려움: 예외 처리가 분산되면 코드의 유지보수가 어려워질 수 있다.
        //HTTP 상태 코드 일관성: 예외 발생 시 항상 200 상태 코드로 응답하게 되면 클라이언트가 에러와 성공 응답을 구분하기 어려울 수 있다.
        //따라서
        //GlobalExceptionHandler를 통해서 예외처리를 한다.
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
