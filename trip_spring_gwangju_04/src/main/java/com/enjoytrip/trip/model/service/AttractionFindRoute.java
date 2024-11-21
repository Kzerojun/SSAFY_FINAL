package com.enjoytrip.trip.model.service;

import com.enjoytrip.trip.model.dto.AttractionDto;

public interface AttractionFindRoute {

	public AttractionDto[]
	findRoute(AttractionDto[] attractions);
}
