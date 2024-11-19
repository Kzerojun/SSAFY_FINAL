package com.enjoytrip.trip.model.dto;

import java.util.List;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;

import lombok.Getter;

@Getter
public class SearchAttractionResponseDto extends ResponseDto {
	
	private List<AttractionDto> attractions;

	public SearchAttractionResponseDto(Code code, Message message, 
			List<AttractionDto> attractions) {
		
		super(code, message);
		this.attractions = attractions;
	}
}
