package com.urida.board.service;

import com.urida.board.dto.ArticleDto;
import com.urida.entity.Board;

import java.util.List;

public interface BoardService {

    // 전체 게시글 목록
    List<Board> getArticles();

    // 게시글 작성
    Board createArticle(ArticleDto articleDto);

    // 게시글 수정
    Board updateArticle(ArticleDto articleDto, Long id);

    // 게시글 삭제
    void deleteArticle(Long id);

    // 좋아요 누르기

    // 좋아요 취소하기

}
