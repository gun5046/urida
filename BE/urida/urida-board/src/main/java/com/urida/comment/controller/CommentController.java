package com.urida.comment.controller;

import com.urida.comment.dto.CommentRequestDto;
import com.urida.comment.entity.Comment;
import com.urida.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequestMapping("/board/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("")

    public Comment createComment(@RequestBody CommentRequestDto CommentRequestDto) {
        return commentService.createComment(CommentRequestDto);
    }

    // 댓글 삭제

    // 댓글 수정
}
