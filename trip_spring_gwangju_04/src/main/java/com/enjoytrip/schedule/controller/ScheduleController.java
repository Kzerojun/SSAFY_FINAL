package com.enjoytrip.schedule.controller;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;
import com.enjoytrip.jwt.service.JwtTokenExtractor;
import com.enjoytrip.jwt.service.JwtTokenFactory;
import com.enjoytrip.schedule.model.dto.*;
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
    //private final JwtTokenFactory jwtTokenFactory;

    @PostMapping()
    public ResponseEntity<ResponseDto> createSchedule(@RequestBody ScheduleDto scheduleDto)
        throws SQLException {

        // TODO: 토큰에서 userEmail을 가져와 세팅
        //scheduleDto.setScheduleId();

        this.scheduleService.createSchedule(scheduleDto);

        ResponseDto responseDto = new ResponseDto(
                Code.SUCCESS,
                Message.SUCCESS
        );

        return ResponseEntity.ok(responseDto);
    }

    // TODO : 일단은 userEmail로 test하고, 이후 Token으로 request 수정
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
