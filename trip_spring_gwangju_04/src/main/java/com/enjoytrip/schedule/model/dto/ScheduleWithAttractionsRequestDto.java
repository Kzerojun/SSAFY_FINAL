package com.enjoytrip.schedule.model.dto;

import com.enjoytrip.trip.model.dto.AttractionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleWithAttractionsRequestDto {

    private String scheduleName;
    private String startDate;
    private String endDate;
    private String userEmail;
    private List<AttractionDto> attractions;
}
