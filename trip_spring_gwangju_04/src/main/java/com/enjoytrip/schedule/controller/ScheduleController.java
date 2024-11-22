package com.enjoytrip.schedule.controller;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;
import com.enjoytrip.jwt.service.JwtTokenFactory;
import com.enjoytrip.schedule.model.dto.*;
import com.enjoytrip.schedule.model.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final JwtTokenFactory jwtTokenFactory;

    @PostMapping()
    public ResponseEntity<ResponseDto> createSchedule(@RequestBody ScheduleDto scheduleDto)
        throws SQLException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        scheduleDto.setUserEmail(authentication.getName());

        this.scheduleService.createSchedule(scheduleDto);

        ResponseDto responseDto = new ResponseDto(
                Code.SUCCESS,
                Message.SUCCESS
        );

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping()
    public ResponseEntity<ListScheduleResponseDto> listSchedule()
        throws SQLException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<ScheduleDto> scheduleDtos = scheduleService.listSchedule(authentication.getName());

        ListScheduleResponseDto listScheduleResponseDto = new ListScheduleResponseDto(
                Code.SUCCESS,
                Message.SUCCESS,
                scheduleDtos
        );

        return ResponseEntity.ok(listScheduleResponseDto);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ResponseDto> updateSchedule(@PathVariable int scheduleId,
        @RequestBody ScheduleDto scheduleDto) throws SQLException {

        scheduleDto.setScheduleId(scheduleId);
        this.scheduleService.updateSchedule(scheduleDto);

        ResponseDto responseDto = new ResponseDto(
                Code.SUCCESS,
                Message.SUCCESS
        );

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ResponseDto> deleteSchedule(@PathVariable int scheduleId)
        throws SQLException {

        this.scheduleService.deleteSchedule(scheduleId);

        ResponseDto responseDto = new ResponseDto(
                Code.SUCCESS,
                Message.SUCCESS
        );

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{scheduleId}/attractions")
    public ResponseEntity<ResponseDto> createAttractionToSchedule(@PathVariable int scheduleId,
       @RequestBody ScheduleAttractionInsertRequestDto scheduleAttractionInsertRequestDto) throws SQLException {

        scheduleAttractionInsertRequestDto.setScheduleId(scheduleId);

        this.scheduleService.createAttractionToSchedule(scheduleAttractionInsertRequestDto);

        ResponseDto responseDto = new ResponseDto(
                Code.SUCCESS,
                Message.SUCCESS
        );

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/with-attractions")
    public ResponseEntity<ResponseDto> createScheduleWithAttractions(
            @RequestBody ScheduleWithAttractionsRequestDto scheduleWithAttractionsRequestDto) throws SQLException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        scheduleWithAttractionsRequestDto.setUserEmail(authentication.getName());

        // Test Code
        System.out.println("USER: " + authentication.getName());
        System.out.println("USER: " + scheduleWithAttractionsRequestDto.getUserEmail());

        int scheduleId = this.scheduleService.createScheduleWithAttractions(scheduleWithAttractionsRequestDto);

        ScheduleWithAttractionsResponseDto responseDto = new ScheduleWithAttractionsResponseDto(
                Code.SUCCESS,
                Message.SUCCESS,
                scheduleId
        );

        // Test Code
        System.out.println("RESPONSE schedule_id: " + responseDto.getScheduleId());

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{scheduleId}/attractions")
    public ResponseEntity<DetailScheduleResponseDto> listScheduleAttractions(@PathVariable int scheduleId)
        throws SQLException {

        String scheduleName = scheduleService.getScheduleName(scheduleId);
        List<ScheduleAttractionDto> attractions = scheduleService.listScheduleAttractions(scheduleId);

        DetailScheduleResponseDto detailScheduleResponseDto = new DetailScheduleResponseDto(
                Code.SUCCESS,
                Message.SUCCESS,
                scheduleName,
                attractions
        );

        // Test Code
        for(ScheduleAttractionDto attractionDto : attractions) {
            System.out.println(attractionDto);
        }

        return ResponseEntity.ok(detailScheduleResponseDto);
    }

    @PatchMapping("/{scheduleId}/attractions/reorder")
    public ResponseEntity<ResponseDto> reorderScheduleAttractions(@PathVariable int scheduleId,
        @RequestBody ScheduleReorderRequestDto scheduleReorderRequestDto) throws SQLException {

        this.scheduleService.updateAttractionOrder(scheduleId, scheduleReorderRequestDto.getAttractions());

        ResponseDto responseDto = new ResponseDto(
                Code.SUCCESS,
                Message.SUCCESS
        );

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{scheduleId}/attractions/{scheduleAttractionId}")
    public ResponseEntity<ResponseDto> deleteAttractionFromSchedule(@PathVariable int scheduleAttractionId)
        throws SQLException {

        this.scheduleService.deleteAttractionFromSchedule(scheduleAttractionId);

        ResponseDto responseDto = new ResponseDto(
                Code.SUCCESS,
                Message.SUCCESS
        );

        return ResponseEntity.ok(responseDto);
    }
}
