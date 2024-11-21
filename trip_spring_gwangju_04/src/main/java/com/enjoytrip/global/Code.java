package com.enjoytrip.global;


import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonValue;

@Getter
public enum Code {

	SUCCESS("SU");

	private final String code;

	Code(String code) {
		this.code = code;
	}

	@JsonValue
	public String getCode() {
		return code;
	}
}
