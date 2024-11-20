package com.enjoytrip.schedule.model.mapper;

import com.enjoytrip.schedule.model.dto.ScheduleAttractionDto;
import com.enjoytrip.schedule.model.dto.ScheduleDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    public List<ScheduleDto> listSchedule(String userEmail) throws SQLException;

    public List<ScheduleAttractionDto> detailSchedule(int scheduleId) throws SQLException;
}
