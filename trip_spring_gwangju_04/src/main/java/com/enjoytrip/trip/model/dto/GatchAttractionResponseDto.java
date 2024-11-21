package com.enjoytrip.trip.model.dto;

import com.enjoytrip.global.ResponseDto;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;

public class GatchAttractionResponseDto extends ResponseDto {

    private AttractionDto attraction;

    public GatchAttractionResponseDto(Code code, Message message,
                                      AttractionDto attraction) {

        super(code, message);
        this.attraction = attraction;
    }

    public AttractionDto getAttraction() {
        return attraction;
    }
}
