package com.enjoytrip.jwt.service;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtAlgorithmProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}
