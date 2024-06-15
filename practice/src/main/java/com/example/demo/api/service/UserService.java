package com.example.demo.api.service;

import com.example.demo.api.request.LoginRequestDto;
import com.example.demo.api.response.LoginResponseDto;
import com.example.demo.common.exception.CommonErrorCode;
import com.example.demo.common.exception.RestApiException;
import com.example.demo.common.jwt.JwtUtil;
import com.example.demo.db.entity.User;
import com.example.demo.db.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public User createUser(User user) {
        String encodePassword = passwordEncoder.encode(user.getUserPassword());
        if(checkEmail(user.getUserEmail())){
            System.out.println("회원 존재");
            throw new RestApiException(CommonErrorCode.MEMBER_FOUND);
        }
        User update_user = new User(user.getUserEmail(), encodePassword, user.getUserName());
        return userRepository.save(update_user);
    }

    /*
      인증과정
      1. 사용자가 제공한 이메일로 데이터베이스에서 사용자를 조회합니다.
      2. 사용자가 존재하지 않으면 예외를 발생시킵니다.
      3. 입력된 비밀번호와 데이터베이스에서 가져온 해시된 비밀번호를 비교하여 일치하지 않으면 예외를 발생시킵니다.
      4. 사용자가 인증되었다고 판단되면 JwtUtil을 사용하여 JWT 토큰을 생성합니다.
      5. 생성된 JWT 토큰과 사용자의 기타 정보를 포함한 응답 객체인 LoginResponseDto를 생성하여 반환합니다.
     */
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        //이메일로 유저를 찾고 없다면 예외
        User loginUser = userRepository.findByUserEmail(loginRequestDto.userEmail).orElseThrow(
                () -> new RestApiException(CommonErrorCode.MEMBER_NOT_FOUND)// 커스텀 예외로 예외처리하자
        );
        //해당 아이디가 있다면 입력된 비밀번호와 DB에 저장된 해시된 비밀번호와 비교를 해보자
        //다르다면 예외처리
        if (!passwordEncoder.matches(loginRequestDto.getUserPassword(), loginUser.getUserPassword())) {
            System.out.println("memger_sign_in_failed 에러");
            throw new RestApiException(CommonErrorCode.MEMBER_SIGN_IN_FAILED);// 커스텀 예외로 예외처리하자
        }

        //여기까지 왔다면 해당 유저는 회원이다.
        //accesstoken을 만들어 보내주자
        /*
            jwt토큰을 만들어 함께 리턴해야한다.
            jwt filter를 통해 JWT를 검증하고 인증 객체를 설정했기에 jwtutil을 사용
         */
        String accesstoken = jwtUtil.generateToken(loginUser.getUserEmail());
        return new LoginResponseDto(accesstoken, loginUser.getUserEmail(), loginUser.getUserName());
    }

    public Boolean checkEmail(String email) {
        return userRepository.findByUserEmail(email).isPresent();
    }

    public List<User> getUser() {
        return userRepository.findAll();
    }
}
