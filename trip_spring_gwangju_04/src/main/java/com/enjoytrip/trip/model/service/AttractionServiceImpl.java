package com.enjoytrip.trip.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.enjoytrip.trip.model.dto.AttractionDto;
import com.enjoytrip.trip.model.mapper.AttractionMapper;


@Service
public class AttractionServiceImpl implements AttractionService {

	private final AttractionMapper attractionMapper;
	
	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		this.attractionMapper = attractionMapper;
	}
	
	// contentTypeId가 0이 아니면 포함 검색, 0이면 불포함 검색
	@Override
	public List<AttractionDto> searchAttraction(AttractionDto attractionDto) throws SQLException {
		return this.attractionMapper.searchAttraction(attractionDto);
	}
	
//	// 입력한 지역(시군구)에 해당하는 관광지 정보 조회
//	@Override
//	public List<AttractionDto> searchAttractionBySidoGugun(String sidoName, String gugunName) {
//		
//		return attractionMapper.searchAttractionBySidoGugun(sidoName, gugunName);
//	}
//	
//	// 입력한 지역(시군구)에 해당하는 관광지 정보 조회
//    @Override
//    public List<AttractionDto> searchAttractionBySidoGugunContentType
//        (String sidoName, String gugunName, String contentType) {
//        
//       return attractionMapper.searchAttractionBySidoGugunContentType(sidoName, gugunName, contentType);
//    }
}
