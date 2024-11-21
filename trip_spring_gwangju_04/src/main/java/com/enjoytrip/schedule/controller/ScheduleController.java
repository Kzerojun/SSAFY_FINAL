package com.enjoytrip.schedule.controller;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.schedule.model.dto.DetailScheduleResponseDto;
import com.enjoytrip.schedule.model.dto.ListScheduleResponseDto;
import com.enjoytrip.schedule.model.dto.ScheduleAttractionDto;
import com.enjoytrip.schedule.model.dto.ScheduleDto;
import com.enjoytrip.schedule.model.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping()
    public ResponseEntity<ListScheduleResponseDto> listSchedule(@RequestParam String userEmail)
        throws SQLException {

        List<ScheduleDto> scheduleDtos = scheduleService.listSchedule(userEmail);

        ListScheduleResponseDto listScheduleResponseDto = new ListScheduleResponseDto(
                Code.SUCCESS,
                Message.SUCCESS,
                scheduleDtos
        );

        return ResponseEntity.ok(listScheduleResponseDto);
    }

    @GetMapping("{scheduleId}/detail")
    public ResponseEntity<DetailScheduleResponseDto> detailSchedule(@PathVariable int scheduleId)
        throws SQLException {

        List<ScheduleAttractionDto> scheduleDetails = scheduleService.detailSchedule(scheduleId);

        DetailScheduleResponseDto detailScheduleResponseDto = new DetailScheduleResponseDto(
                Code.SUCCESS,
                Message.SUCCESS,
                scheduleDetails
        );

        return ResponseEntity.ok(detailScheduleResponseDto);
    }
}
