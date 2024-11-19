package com.enjoytrip.trip.model.service;

import com.enjoytrip.trip.model.dto.AttractionDto;

public interface FindRouteService {

	public AttractionDto[] findRoute(AttractionDto[] attractions);
}
