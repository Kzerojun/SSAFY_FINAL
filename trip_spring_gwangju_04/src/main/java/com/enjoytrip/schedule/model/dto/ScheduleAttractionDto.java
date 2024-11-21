package com.enjoytrip.schedule.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ScheduleAttractionDto {

    private int scheduleAttractionId;
    private String title;
    private double latitude;
    private double longitude;
    private String firstImage1;
    private String firstImage2;
    private String addr1;
    private String addr2;
    private int scheduleId;
    private int sequenceOrder;

    @Override
    public String toString() {
        return "[" + this.getSequenceOrder() + ": " + this.getTitle() + "]";
    }
}
