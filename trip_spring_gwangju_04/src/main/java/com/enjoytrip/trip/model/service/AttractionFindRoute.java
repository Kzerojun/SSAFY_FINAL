package com.enjoytrip.trip.model.service;

import com.enjoytrip.trip.model.AttractionDto;

public interface AttractionFindRoute {

	public AttractionDto[]
	findRoute(AttractionDto[] attractions);
}
