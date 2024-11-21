package com.enjoytrip.schedule.model.service;

import com.enjoytrip.schedule.model.dto.AttractionOrderDto;
import com.enjoytrip.schedule.model.dto.ScheduleAttractionDto;
import com.enjoytrip.schedule.model.dto.ScheduleAttractionInsertRequestDto;
import com.enjoytrip.schedule.model.dto.ScheduleDto;
import com.enjoytrip.schedule.model.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;

    @Override
    public int createSchedule(ScheduleDto scheduleDto) throws SQLException {
        return this.scheduleMapper.createSchedule(scheduleDto);
    }

    @Override
    public List<ScheduleDto> listSchedule(String userEmail) throws SQLException {
        return this.scheduleMapper.listSchedule(userEmail);
    }

    @Override
    public int updateSchedule(ScheduleDto scheduleDto) throws SQLException {
        return this.scheduleMapper.updateSchedule(scheduleDto);
    }

    @Override
    public int deleteSchedule(int scheduleId) throws SQLException {
        return this.scheduleMapper.deleteSchedule(scheduleId);
    }

    @Override
    public int createAttractionToSchedule(ScheduleAttractionInsertRequestDto scheduleAttractionInsertRequestDto)
            throws SQLException {

        int maxSequence = this.scheduleMapper.getMaxSequence(scheduleAttractionInsertRequestDto.getScheduleId());

        scheduleAttractionInsertRequestDto.setSequenceOrder(maxSequence + 1);

        return this.scheduleMapper.createAttractionToSchedule(scheduleAttractionInsertRequestDto);
    }

    @Override
    public List<ScheduleAttractionDto> listScheduleAttractions(int scheduleId) throws SQLException {
        return this.scheduleMapper.listScheduleAttractions(scheduleId);
    }

    @Override
    public String getScheduleName(int scheduleId) throws SQLException {
        return this.scheduleMapper.getScheduleName(scheduleId);
    }

    @Override
    public void updateAttractionOrder(int scheduleId, List<AttractionOrderDto> attractions) throws SQLException {

        for(AttractionOrderDto attractionOrderDto : attractions) {
            this.scheduleMapper.updateSequenceOrder(attractionOrderDto);
        }
    }

    @Override
    public int deleteAttractionFromSchedule(int scheduleAttractionId) throws SQLException {
        return this.scheduleMapper.deleteAttractionFromSchedule(scheduleAttractionId);
    }
}
