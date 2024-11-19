package com.enjoytrip.trip.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.trip.model.dto.AttractionDto;

@Mapper
public interface AttractionMapper {

//	public List<AttractionDto> 
//	searchAttractionBySidoGugun(String sidoName, String gugunName) throws SQLException;
	
//	public List<AttractionDto> 
//	searchAttractionBySidoGugunContentType(AttractionDto attractionDto) throws SQLException;
	
	public List<AttractionDto> searchAttraction(AttractionDto attractionDto) throws SQLException;
}
