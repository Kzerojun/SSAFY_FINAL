package com.enjoytrip.schedule.model.dto;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;
import lombok.Getter;

@Getter
public class ScheduleWithAttractionsResponseDto extends ResponseDto {

    private final int scheduleId;

    public ScheduleWithAttractionsResponseDto(Code code, Message message, int scheduleId) {
        super(code, message);
        this.scheduleId = scheduleId;
    }
}
