package com.enjoytrip.schedule.model.dto;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ListScheduleResponseDto extends ResponseDto {

    private final List<ScheduleDto> schedules;

    public ListScheduleResponseDto(Code code, Message message,
                            List<ScheduleDto> schedules) {
        super(code, message);
        this.schedules = schedules;
    }
}
