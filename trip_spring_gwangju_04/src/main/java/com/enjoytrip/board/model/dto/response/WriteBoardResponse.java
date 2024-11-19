package com.enjoytrip.board.model.dto.response;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class WriteBoardResponse extends ResponseDto {
	
	private WriteBoardResponse(Code code, Message message) {
		super(code,message);
	}
	
	public static WriteBoardResponse success(Code code, Message message) {
		return new WriteBoardResponse(code,message);
	}
	

	

}
