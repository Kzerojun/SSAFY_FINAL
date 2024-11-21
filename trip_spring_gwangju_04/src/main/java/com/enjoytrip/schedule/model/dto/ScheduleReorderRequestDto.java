package com.enjoytrip.schedule.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ScheduleReorderRequestDto {

    private int scheduleId;
    private List<AttractionOrderDto> attractions;
}
