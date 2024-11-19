package com.enjoytrip.board.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WriteBoardRequest {
    private String userEmail;
    private String title;
    private String content;
    private String category;
}