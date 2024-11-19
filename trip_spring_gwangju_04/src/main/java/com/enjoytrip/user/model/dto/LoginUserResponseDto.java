package com.enjoytrip.user.model.dto;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;

import lombok.Getter;

@Getter
public class LoginUserResponseDto extends ResponseDto {
	
	private String userEmail;
	private String userName;
	// Token에는 userNo, userEmail, userName 포함
	//private String token;

	public LoginUserResponseDto(Code code, Message message, 
			String userEmail, String userName) {
		super(code, message);
		this.userEmail = userEmail;
		this.userName = userName;
		//this.token = token;
	}

}
