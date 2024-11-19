package com.enjoytrip.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.enjoytrip.board.model.dto.BoardDto;
import com.enjoytrip.board.model.dto.request.WriteBoardRequest;
import com.enjoytrip.board.model.dto.response.WriteBoardResponse;
import com.enjoytrip.board.service.BoardService;
import com.enjoytrip.global.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "게시판 API", description = "게시판 CRUD API")
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    
    @Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "게시글 작성 성공", 
            content = @Content(schema = @Schema(implementation = WriteBoardResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/write")
    public ResponseEntity<? extends ResponseDto> write(
            @Parameter(description = "게시글 작성 정보", required = true) 
            @RequestBody WriteBoardRequest writeBoardRequest) {
        WriteBoardResponse response = boardService.writeBoard(writeBoardRequest);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "게시글 상세 조회", description = "게시글 번호로 게시글을 상세 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "게시글 조회 성공",
            content = @Content(schema = @Schema(implementation = BoardDto.class))),
        @ApiResponse(responseCode = "404", description = "게시글 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{articleNo}")
    public ResponseEntity<BoardDto> getArticle(
            @Parameter(description = "게시글 번호", required = true)
            @PathVariable int articleNo) {
        return ResponseEntity.ok(boardService.getArticle(articleNo));
    }
    
    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ResponseEntity<List<BoardDto>> listArticle(
            @Parameter(description = "검색 조건")
            @RequestParam Map<String, Object> param) {
        return ResponseEntity.ok(boardService.listArticle(param));
    }
    
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "게시글 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/{articleNo}")
    public ResponseEntity<Void> updateArticle(
            @Parameter(description = "게시글 번호", required = true)
            @PathVariable int articleNo,
            @Parameter(description = "수정할 게시글 정보", required = true)
            @RequestBody BoardDto boardDto) {
        boardDto.setArticleNo(articleNo);
        boardService.updateArticle(boardDto);
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "게시글 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/{articleNo}")
    public ResponseEntity<Void> deleteArticle(
            @Parameter(description = "게시글 번호", required = true)
            @PathVariable int articleNo) {
        boardService.deleteArticle(articleNo);
        return ResponseEntity.ok().build();
    }
}