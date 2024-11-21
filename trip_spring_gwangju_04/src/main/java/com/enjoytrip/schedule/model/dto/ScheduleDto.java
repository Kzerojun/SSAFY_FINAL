package com.enjoytrip.schedule.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDto {

    private int scheduleId;
    private String userEmail;
    private String scheduleName;
    private String startDate;
    private String endDate;
    private String createdAt;
}
