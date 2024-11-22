package com.enjoytrip.board.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class WriteBoardRequest {

    private String userEmail;
    private String subject;
    private String content;
    private List<String> filePaths;
    private String category;

}