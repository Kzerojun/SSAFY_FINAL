package com.enjoytrip.schedule.model.service;

import com.enjoytrip.schedule.model.dto.ScheduleAttractionDto;
import com.enjoytrip.schedule.model.dto.ScheduleDto;
import com.enjoytrip.schedule.model.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;

    @Override
    public List<ScheduleDto> listSchedule(String userEmail) throws SQLException {
        return this.scheduleMapper.listSchedule(userEmail);
    }

    @Override
    public List<ScheduleAttractionDto> detailSchedule(int scheduleId) throws SQLException {
        return this.scheduleMapper.detailSchedule(scheduleId);
    }
}
