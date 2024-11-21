package com.enjoytrip.jwt.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtConstants {
    AUTHORIZATION_HEADER("Authorization"),
    BEARER_TYPE("Bearer "),
    ROLE("roles");

    // 필드 정의
    private final String value;

}
