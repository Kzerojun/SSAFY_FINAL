package com.enjoytrip.trip.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;

import com.enjoytrip.trip.model.dto.AttractionDto;
import com.enjoytrip.trip.model.dto.SearchAttractionResponseDto;
import com.enjoytrip.trip.model.service.AttractionService;
import com.enjoytrip.trip.model.service.FindRouteService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/trip")
@RequiredArgsConstructor
public class TripController {
	
	private final AttractionService attractionService;
	private final FindRouteService findRouteService;
	
	@GetMapping("/search")
	public ResponseEntity<SearchAttractionResponseDto> search(@RequestParam int sido, 
			@RequestParam int gugun, 
			@RequestParam(defaultValue = "0") int content) throws SQLException{
		
		AttractionDto attraction = new AttractionDto();
		attraction.setAreaCode(sido);
		attraction.setSiGunGuCode(gugun);
		attraction.setContentTypeId(content);
		
		List<AttractionDto> attractions = this.attractionService.searchAttraction(attraction);
		
		SearchAttractionResponseDto searchAttractionDto = new SearchAttractionResponseDto(
				Code.SUCCESS,
				Message.SUCCESS,
				attractions
		);
		
		return ResponseEntity.ok(searchAttractionDto);
	}
	
}
