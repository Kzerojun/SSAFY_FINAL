package com.enjoytrip.jwt.service;

import com.auth0.jwt.JWT;
import com.enjoytrip.auth.dto.request.LoginRequest;
import com.enjoytrip.jwt.constants.JwtConstants;
import com.enjoytrip.security.service.AuthenticationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenFactory {

    private final long ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 7; // 7 days
    private final JwtAlgorithmProvider jwtAlgorithmProvider;

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));


        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_SECONDS);

        return  JWT.create()
                .withSubject(username)
                .withClaim(JwtConstants.ROLE.getValue(), roles)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(jwtAlgorithmProvider.getAlgorithm());
    }

}
