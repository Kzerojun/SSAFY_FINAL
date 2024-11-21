package com.enjoytrip.auth.service;

import com.enjoytrip.auth.dto.request.LoginRequest;
import com.enjoytrip.auth.dto.request.SignUpRequest;
import com.enjoytrip.auth.dto.response.LoginResponse;
import com.enjoytrip.auth.dto.response.SignUpResponse;
import com.enjoytrip.auth.model.mapper.AuthMapper;
import com.enjoytrip.jwt.service.JwtTokenFactory;
import com.enjoytrip.security.service.AuthenticationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImplement implements AuthService {

    private final JwtTokenFactory jwtTokenFactory;
    private final AuthenticationProcessor authenticationProcessor;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    @Override
    public LoginResponse login(LoginRequest request) {

        //TODO : Spring Security 오류
        Authentication authentication = authenticationProcessor.authenticate(request);
        String token = jwtTokenFactory.generateToken(authentication);
        return LoginResponse.success(token);
    }


    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        String encode = passwordEncoder.encode(request.getPassword());
        request.setPassword(encode);
        authMapper.signUp(request);

        return SignUpResponse.success();
    }
}
