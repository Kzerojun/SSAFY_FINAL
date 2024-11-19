package com.enjoytrip.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enjoytrip.board.model.dto.BoardDto;
import com.enjoytrip.board.model.dto.request.WriteBoardRequest;
import com.enjoytrip.board.model.dto.response.WriteBoardResponse;
import com.enjoytrip.board.model.mapper.BoardMapper;
import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    @Transactional
    public WriteBoardResponse writeBoard(WriteBoardRequest writeBoardRequest) {
        try {
            boardMapper.writeBoard(writeBoardRequest);
            return WriteBoardResponse.success(Code.SUCCESS,Message.SUCCESS);
        } catch (Exception e) {
            log.error("게시글 작성 중 오류 발생", e);
            throw new RuntimeException("게시글 조회 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public BoardDto getArticle(int articleNo) {
        try {
            boardMapper.updateHit(articleNo);
            return boardMapper.getArticle(articleNo);
        } catch (Exception e) {
            log.error("게시글 조회 중 오류 발생", e);
            throw new RuntimeException("게시글 조회 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public List<BoardDto> listArticle(Map<String, Object> param) {
        try {
            return boardMapper.listArticle(param);
        } catch (Exception e) {
            log.error("게시글 목록 조회 중 오류 발생", e);
            throw new RuntimeException("게시글 목록 조회 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void updateArticle(BoardDto boardDto) {
        try {
            boardMapper.updateArticle(boardDto);
        } catch (Exception e) {
            log.error("게시글 수정 중 오류 발생", e);
            throw new RuntimeException("게시글 수정 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void deleteArticle(int articleNo) {
        try {
            boardMapper.deleteArticle(articleNo);
        } catch (Exception e) {
            log.error("게시글 삭제 중 오류 발생", e);
            throw new RuntimeException("게시글 삭제 중 오류가 발생했습니다.", e);
        }
    }
}