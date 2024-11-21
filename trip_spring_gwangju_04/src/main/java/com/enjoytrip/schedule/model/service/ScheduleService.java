package com.enjoytrip.schedule.model.service;

import com.enjoytrip.schedule.model.dto.AttractionOrderDto;
import com.enjoytrip.schedule.model.dto.ScheduleAttractionDto;
import com.enjoytrip.schedule.model.dto.ScheduleAttractionInsertRequestDto;
import com.enjoytrip.schedule.model.dto.ScheduleDto;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleService {

    public int createSchedule(ScheduleDto scheduleDto) throws SQLException;

    public List<ScheduleDto> listSchedule(String userEmail)
            throws SQLException;

    public int updateSchedule(ScheduleDto scheduleDto) throws SQLException;

    public int deleteSchedule(int scheduleId) throws SQLException;

    public int createAttractionToSchedule(ScheduleAttractionInsertRequestDto scheduleAttractionInsertRequestDto) throws SQLException;

    public List<ScheduleAttractionDto> listScheduleAttractions(int scheduleId)
            throws SQLException;

    public String getScheduleName(int scheduleId) throws SQLException;

    public void updateAttractionOrder(int scheduleId, List<AttractionOrderDto> attractions) throws SQLException;

    public int deleteAttractionFromSchedule(int scheduleAttractionId) throws SQLException;
}
