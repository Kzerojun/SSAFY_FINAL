package com.enjoytrip.jwt.service;

import com.enjoytrip.jwt.constants.JwtConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import static com.enjoytrip.jwt.constants.JwtConstants.AUTHORIZATION_HEADER;
import static com.enjoytrip.jwt.constants.JwtConstants.BEARER_TYPE;

@Service
public class JwtTokenExtractor {

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER.getValue());

        if (token != null && token.startsWith(BEARER_TYPE.getValue())) {
            return token.substring(7);
        }
        return null;
    }

}
