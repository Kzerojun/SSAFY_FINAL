package com.enjoytrip.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Code {
	
	SUCCESS("200");
	
	private final String code;
}

