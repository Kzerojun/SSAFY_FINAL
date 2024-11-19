package com.enjoytrip.board.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBoardRequest {
    private int articleNo;
    private String title;
    private String content;
}