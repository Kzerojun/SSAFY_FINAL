package com.enjoytrip.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.board.model.dto.BoardDto;
import com.enjoytrip.board.model.dto.request.WriteBoardRequest;

@Mapper
public interface BoardMapper {
    // 만들기
    void writeBoard(WriteBoardRequest writeBoardRequest) throws SQLException;
    
    // 읽기
    BoardDto getArticle(int articleNo) throws SQLException;
    List<BoardDto> listArticle(Map<String, Object> param) throws SQLException;
    int getTotalArticleCount(Map<String, Object> param) throws SQLException;
    
    // 업데이트
    void updateHit(int articleNo) throws SQLException;
    void updateArticle(BoardDto boardDto) throws SQLException;
    
    // 삭제
    void deleteArticle(int articleNo) throws SQLException;
}