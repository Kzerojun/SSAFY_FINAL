package com.enjoytrip.auth.dto.response;


import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;
import lombok.Getter;

@Getter
public class SignUpResponse extends ResponseDto {

    private SignUpResponse(Code code, Message message) {
        super(code,message);
    }

    public static SignUpResponse success() {
        return new SignUpResponse(Code.SUCCESS,Message.SUCCESS);
    }
}
