package com.enjoytrip.security.service;


import com.enjoytrip.auth.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationProcessor {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public Authentication authenticate(LoginRequest loginRequest) {

        // LoginRequest를 AuthenticationToken으로 변환
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

        // AuthenticationManager를 사용하여 인증 수행
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }
}
