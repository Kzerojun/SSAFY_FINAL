package com.enjoytrip.jwt.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenValidator {

    @Value("${spring.jwt.secret}")
    private String secret;

    private final JwtAlgorithmProvider provider;

    public DecodedJWT validate(String token) {

        try{
            JWTVerifier verifier = JWT.require(provider.getAlgorithm()).build();
            return verifier.verify(token);

        } catch (TokenExpiredException e) {
            System.out.println("Token has expired: " + e.getMessage());
        } catch (JWTVerificationException e) {
            System.out.println("Invalid Token: " + e.getMessage());
        }

        return null;
    }
}
