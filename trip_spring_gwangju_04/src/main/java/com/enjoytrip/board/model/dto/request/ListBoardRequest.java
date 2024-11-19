package com.enjoytrip.board.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListBoardRequest {
    private int page = 1;
    private int listSize = 10;
    private String key;
    private String word;
    
    public int getStart() {
        return (page - 1) * listSize;
    }
}