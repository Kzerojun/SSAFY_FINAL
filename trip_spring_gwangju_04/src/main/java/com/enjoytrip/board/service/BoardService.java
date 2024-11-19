package com.enjoytrip.board.service;

import java.util.List;
import java.util.Map;

import com.enjoytrip.board.model.dto.BoardDto;
import com.enjoytrip.board.model.dto.request.WriteBoardRequest;
import com.enjoytrip.board.model.dto.response.WriteBoardResponse;

public interface BoardService {
    WriteBoardResponse writeBoard(WriteBoardRequest writeBoardRequest);
    BoardDto getArticle(int articleNo);
    List<BoardDto> listArticle(Map<String, Object> param);
    void updateArticle(BoardDto boardDto);
    void deleteArticle(int articleNo);
}