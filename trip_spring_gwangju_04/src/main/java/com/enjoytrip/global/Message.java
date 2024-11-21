package com.enjoytrip.global;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {

    SUCCESS("성공");

    private final String message;

    @JsonValue
    public String getMessage() {
        return message;
    }
}