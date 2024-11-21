package com.enjoytrip.auth.service;

import com.enjoytrip.auth.dto.request.LoginRequest;
import com.enjoytrip.auth.dto.request.SignUpRequest;
import com.enjoytrip.auth.dto.response.LoginResponse;
import com.enjoytrip.auth.dto.response.SignUpResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
    SignUpResponse signUp(SignUpRequest request);
}
