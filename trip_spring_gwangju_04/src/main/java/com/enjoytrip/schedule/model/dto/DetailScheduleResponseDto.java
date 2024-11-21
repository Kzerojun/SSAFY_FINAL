package com.enjoytrip.schedule.model.dto;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class DetailScheduleResponseDto extends ResponseDto {

    private final String scheduleName;
    private final List<ScheduleAttractionDto> scheduleAttractions;

    public DetailScheduleResponseDto(Code code, Message message, String scheduleName,
                              List<ScheduleAttractionDto> scheduleAttractions) {
        super(code, message);
        this.scheduleName = scheduleName;
        this.scheduleAttractions = scheduleAttractions;
    }
}
