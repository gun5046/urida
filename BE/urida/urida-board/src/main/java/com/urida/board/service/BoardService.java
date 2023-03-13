package com.urida.board.service;

import com.urida.board.dto.ArticleRequestDto;
import com.urida.board.dto.ArticleUpdateDto;
import com.urida.board.entity.Board;

import java.util.List;

public interface BoardService {

    // 전체 게시글 목록
    List<Board> getArticles();

    // 개별 게시글 조회
    Board getArticle(Long id);

    // 게시글 작성
    Board createArticle(ArticleRequestDto articleRequestDto);

    // 게시글 수정
    Board updateArticle(ArticleUpdateDto articleUpdateDto, Long id);

    // 게시글 삭제
    void deleteArticle(Long id);

    // 좋아요 누르기

    // 좋아요 취소하기

}
