package com.urida.board.service;

import com.urida.board.dto.request.ArticleCreateDto;
import com.urida.board.dto.request.ArticleUpdateDto;
import com.urida.board.dto.response.BoardDetailDto;
import com.urida.board.dto.response.BoardListDto;
import com.urida.board.entity.Board;

import java.util.List;

public interface BoardService {

    // 전체 게시글 목록
    List<BoardListDto> getArticles();

    // 개별 게시글 조회
    BoardDetailDto getArticle(Long id);

    // 게시글 조회 수 증가
   /* void increaseView(Long id);*/

    // 게시글 작성
    Board createArticle(ArticleCreateDto articleCreateDto);

    // 게시글 수정
    Board updateArticle(ArticleUpdateDto articleUpdateDto, Long id);

    // 게시글 삭제
    void deleteArticle(Long id);

    // 좋아요 누르기
    Boolean clickLikeArticle(Long board_id,Long uid, Boolean status);

    // 좋아요 개수
    int likeCnt(Long board_id);

    // 좋아요 누르기
//    int likeArticle(Long id);

    // 좋아요 취소하기
//    int dislikeArticle(Long id);

}
