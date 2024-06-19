package com.example.demo.common;

import com.example.demo.common.dto.CustomUserDetail;
import com.example.demo.common.exception.CommonErrorCode;
import com.example.demo.common.exception.RestApiException;
import com.example.demo.db.entity.User;
import com.example.demo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuntomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(
                ()-> new RestApiException(CommonErrorCode.MEMBER_NOT_FOUND)
        );
        return new CustomUserDetail(user);
    }
}
