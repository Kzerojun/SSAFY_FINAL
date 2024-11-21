package com.enjoytrip.auth.dto.response;

import com.enjoytrip.auth.dto.request.LoginRequest;
import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;
import lombok.Getter;

@Getter
public class LoginResponse extends ResponseDto {

    private String accessToken;

    private LoginResponse(Code code, Message message,String accessToken) {
        super(code,message);
        this.accessToken = accessToken;

    }

    public static LoginResponse success(String accessToken) {
        return new LoginResponse(Code.SUCCESS, Message.SUCCESS, accessToken);
    }



}
