package com.enjoytrip.user.model.dto;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;

import lombok.Getter;

@Getter
public class LoginUserResponseDto extends ResponseDto {
	
	private String email;


	private LoginUserResponseDto(Code code, Message message,
			String email) {
		super(code, message);
		this.email = email;
	}

	public static LoginUserResponseDto success(String email) {
		return new LoginUserResponseDto(Code.SUCCESS,Message.SUCCESS,email);
	}

}
