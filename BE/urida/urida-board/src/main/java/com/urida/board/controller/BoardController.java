package com.urida.board.controller;

import com.urida.board.dto.ArticleRequestDto;
import com.urida.board.dto.ArticleUpdateDto;
import com.urida.board.service.BoardService;
import com.urida.board.entity.Board;
import com.urida.exception.InputException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 전체 게시글 조회
    @GetMapping("/list")

    public List<Board> boardList() {
        List<Board> list = boardService.getArticles();
        int listSize = list.size();
        return list;
    }

    // 개별 게시글 조회
    @GetMapping("/{id}")
    public Board getArticle(@PathVariable Long id) {
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
    public Board createArticle(@Validated @RequestBody ArticleRequestDto articleRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputException("RequestData(ArticleDto)invalid");
        }
        return boardService.createArticle(articleRequestDto);
    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable Long id, @RequestBody ArticleUpdateDto articleUpdateDto) {
        Board article = boardService.updateArticle(articleUpdateDto, id);
    }
}
