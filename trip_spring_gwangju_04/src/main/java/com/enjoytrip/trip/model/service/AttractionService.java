package com.enjoytrip.trip.model.service;

import java.sql.SQLException;
import java.util.List;

import com.enjoytrip.trip.model.dto.AttractionDto;

public interface AttractionService {

//	public List<AttractionDto> 
//	searchAttractionBySidoGugun(String sidoName, String gugunName) throws SQLException;
//	
//	public List<AttractionDto> 
//	searchAttractionBySidoGugunContentType(String sidoName, String gugunName, String contentType);
	
	public List<AttractionDto> searchAttraction(AttractionDto attractionDto) throws SQLException;
}
