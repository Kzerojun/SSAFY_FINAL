package com.enjoytrip.schedule.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ScheduleAttractionInsertRequestDto {

    private int scheduleId;
    private int attractionId;
    private int sequenceOrder;
}
