package com.enjoytrip.schedule.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ScheduleAttractionDto {

    private String title;
    private double latitude;
    private double longitude;
    private String firstImage1;
    private String firstImage2;
    private int scheduleId;
    private int sequenceOrder;
}
