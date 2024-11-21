package com.enjoytrip.schedule.model.mapper;

import com.enjoytrip.schedule.model.dto.AttractionOrderDto;
import com.enjoytrip.schedule.model.dto.ScheduleAttractionDto;
import com.enjoytrip.schedule.model.dto.ScheduleAttractionInsertRequestDto;
import com.enjoytrip.schedule.model.dto.ScheduleDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    public int createSchedule(ScheduleDto scheduleDto) throws SQLException;

    public List<ScheduleDto> listSchedule(String userEmail)
            throws SQLException;

    public int updateSchedule(ScheduleDto scheduleDto) throws SQLException;

    public int deleteSchedule(int scheduleId) throws SQLException;

    // 해당 일정 내 관광지들 중 마지막 순서를 찾는 쿼리
    public int getMaxSequence(int scheduleId) throws SQLException;

    public int createAttractionToSchedule(ScheduleAttractionInsertRequestDto scheduleAttractionInsertRequestDto);

    public List<ScheduleAttractionDto> listScheduleAttractions(int scheduleId)
            throws SQLException;

    public String getScheduleName(int scheduleId) throws SQLException;

    public int updateSequenceOrder(AttractionOrderDto attractionOrderDto) throws SQLException;

    public int deleteAttractionFromSchedule(int scheduleAttractionId) throws SQLException;
}
