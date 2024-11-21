package com.enjoytrip.trip.controller;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.trip.model.dto.AttractionDto;
import com.enjoytrip.trip.model.dto.GatchAttractionResponseDto;
import com.enjoytrip.trip.model.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("/gatcha")
@RequiredArgsConstructor
public class GatchaController {

    private final AttractionService attractionService;

    @PostMapping
    public ResponseEntity<GatchAttractionResponseDto> gatcha(@RequestBody Map<String, Integer> request)
            throws SQLException {

        AttractionDto attractionDto = new AttractionDto();
        attractionDto.setAreaCode(request.get("sido"));
        attractionDto.setSiGunGuCode(request.get("gugun"));
        attractionDto.setContentTypeId(request.get("content"));

        AttractionDto gatchaAttraction = this.attractionService.gatchAttraction(attractionDto);

        GatchAttractionResponseDto gatchAttractionResponseDto = new GatchAttractionResponseDto(
                Code.SUCCESS,
                Message.SUCCESS,
                gatchaAttraction
        );

        // Test
        System.out.println("GATCHA!!: " + gatchAttractionResponseDto.getAttraction());

        return ResponseEntity.ok(gatchAttractionResponseDto);
    }
}
