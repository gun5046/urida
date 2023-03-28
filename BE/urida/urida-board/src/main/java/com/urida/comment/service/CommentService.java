package com.urida.comment.service;


import com.urida.comment.dto.CommentRequestDto;
import com.urida.comment.dto.CommentResponseDto;
import com.urida.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(CommentRequestDto commentRequestDto);

    List<CommentResponseDto> getComments(Long board_id);

    Comment updateComment(Long comment_id, CommentRequestDto commentRequestDto);

    int commentCnt(Long board_id);

    Long removeComment(Long comment_id);

}
