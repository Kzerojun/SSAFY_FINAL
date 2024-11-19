package com.enjoytrip.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {
	
    SUCCESS("SUCCESS!!");

    private final String message;
}