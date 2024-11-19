package com.enjoytrip.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
	
	private Code code;
	private Message message;
}
