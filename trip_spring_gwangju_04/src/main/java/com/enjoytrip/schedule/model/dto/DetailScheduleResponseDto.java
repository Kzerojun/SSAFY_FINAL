package com.enjoytrip.schedule.model.dto;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class DetailScheduleResponseDto extends ResponseDto {

    private final List<ScheduleAttractionDto> scheduleAttractions;

    public DetailScheduleResponseDto(Code code, Message message,
                              List<ScheduleAttractionDto> scheduleAttractions) {
        super(code, message);
        this.scheduleAttractions = scheduleAttractions;
    }
}
