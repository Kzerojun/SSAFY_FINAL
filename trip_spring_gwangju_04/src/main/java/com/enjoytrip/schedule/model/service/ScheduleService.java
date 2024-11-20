package com.enjoytrip.schedule.model.service;

import com.enjoytrip.schedule.model.dto.ScheduleAttractionDto;
import com.enjoytrip.schedule.model.dto.ScheduleDto;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleService {

    public List<ScheduleDto> listSchedule(String userEmail) throws SQLException;

    public List<ScheduleAttractionDto> detailSchedule(int scheduleId) throws SQLException;
}
