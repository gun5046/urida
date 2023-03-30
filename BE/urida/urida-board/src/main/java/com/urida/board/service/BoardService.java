package com.urida.board.service;

import com.urida.board.dto.request.ArticleCreateDto;
import com.urida.board.dto.request.ArticleUpdateDto;
import com.urida.board.dto.response.BoardDetailDto;
import com.urida.board.dto.response.BoardListDto;
import com.urida.board.entity.Board;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    // 전체 게시글 목록
    List<BoardListDto> getArticles(int category_id);

    // 개별 게시글 조회
    BoardDetailDto getArticle(Long id);

    // 유저가 쓴 글 조회
    List<BoardListDto> getArticlesByUser(Long uid, int category_id);

    // 유저가 댓글 남긴 글 카테코리 별 조회
    List<BoardListDto> getArticleByUserCommentedOn(Long uid, int category_id);

    // 게시글 작성
    Board createArticle(ArticleCreateDto articleCreateDto) throws IOException;

    // 게시글 수정
    Board updateArticle(ArticleUpdateDto articleUpdateDto, Long id);

    // 게시글 삭제
    void deleteArticle(Long id);

    // 좋아요 누르기
    Boolean clickLikeArticle(Long board_id, Long uid);

    // 좋아요 개수
    int likeCnt(Long board_id);

    // 게시글 좋아요 여부
    Boolean isLiked(Long board_id, Long uid);

}
