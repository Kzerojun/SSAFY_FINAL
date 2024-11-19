package com.enjoytrip.user.model.dto;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;

import lombok.Getter;

@Getter
public class GetUserProfileResponseDto extends ResponseDto {

	private UserDto userDto;
	
	public GetUserProfileResponseDto(Code code, Message message, UserDto userDto) {
		super(code, message);
		this.userDto = userDto;
	}
}
