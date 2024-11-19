package com.enjoytrip.trip.model.service;

import java.util.List;

import com.enjoytrip.trip.model.AttractionContentTypeDto;

public interface AttractionContentTypeService {

	public List<AttractionContentTypeDto> 
	searchAttractionBySigunguContentTypeName(String sidoName, String gugunName, String contentType);
}
