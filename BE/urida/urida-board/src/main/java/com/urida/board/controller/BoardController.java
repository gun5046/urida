package com.urida.board.controller;

import com.urida.board.dto.request.ArticleCreateDto;
import com.urida.board.dto.request.ArticleUpdateDto;
import com.urida.board.dto.response.BoardDetailDto;
import com.urida.board.dto.response.BoardListDto;
import com.urida.board.service.BoardService;
import com.urida.board.entity.Board;
import com.urida.exception.InputException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 카테고리 별 게시글 리스트 조회
    @GetMapping("/{category_id}/list")
    public List<BoardListDto> boardList(@PathVariable int category_id) {
        List<BoardListDto> list = boardService.getArticles(category_id);
        int listSize = list.size();
        return list;
    }

    // 개별 게시글 조회
    @GetMapping("/{id}")
    public BoardDetailDto getArticle(@PathVariable Long id) {
        return boardService.getArticle(id);
    }

    // 게시글 작성
    @PostMapping("/create")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "사용자 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public Board createArticle(@Validated @ModelAttribute ArticleCreateDto articleCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputException("RequestData(ArticleDto)invalid");
        }
        return boardService.createArticle(articleCreateDto);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public BoardDetailDto updateArticle(@PathVariable Long id, @RequestBody ArticleUpdateDto articleUpdateDto) {
        Board article = boardService.updateArticle(articleUpdateDto, id);
        return boardService.getArticle(id);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id) {
//        Long targetId = boardService.getArticle(id).getBoard_id();
        boardService.deleteArticle(id);
    }

// 게시글 좋아요
    @PutMapping("/like/{board_id}/{uid}")
    public Boolean clickLikeArticle(@PathVariable Long board_id, @PathVariable Long uid) {
        return boardService.clickLikeArticle(board_id,uid);
    }

    // 유저 게시물 좋아요 여부
    @GetMapping("/{board_id}/{uid}")
    public Boolean isLiked(@PathVariable Long board_id, @PathVariable Long uid) {
        return boardService.isLiked(board_id, uid);
    }
}
