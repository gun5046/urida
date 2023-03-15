package com.urida.comment.service;


import com.urida.comment.dto.CommentRequestDto;
import com.urida.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(CommentRequestDto commentRequestDto);

    Comment updateComment(CommentRequestDto commentRequestDto);

    int removeComment(Long comment_id);
}
