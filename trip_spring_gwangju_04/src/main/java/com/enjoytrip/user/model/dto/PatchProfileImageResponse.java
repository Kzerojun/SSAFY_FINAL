package com.enjoytrip.user.model.dto;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;
import lombok.Getter;

@Getter
public class PatchProfileImageResponse extends ResponseDto {

	private PatchProfileImageResponse(Code code, Message message) {
		super(code, message);
	}

	public static PatchProfileImageResponse success() {
		return new PatchProfileImageResponse(Code.SUCCESS, Message.SUCCESS);
	}
}
