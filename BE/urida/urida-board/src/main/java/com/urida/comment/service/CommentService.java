package com.urida.comment.service;


import com.urida.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getComments(Long board_id);

    int removeComment(Long comment_id);
}
