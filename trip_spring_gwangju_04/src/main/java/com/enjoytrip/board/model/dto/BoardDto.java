package com.enjoytrip.board.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDto {
    private int articleNo;
    private String userEmail;  // userId -> userEmail로 변경
    private String userName;
    private String subject;
    private String content;
    private int hit;
    private String registerTime;
}