package com.urida.board.dto.response;

import com.urida.comment.dto.CommentResponseDto;
import com.urida.comment.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BoardDetailDto {
    private Long board_id;

    private String title;

    private String content;

    private String image;

    private int view;

    private String dateTime;

    private int likeCnt;

    private String nickname;

    private List<CommentResponseDto> comment;

    private int commentCnt;
}
