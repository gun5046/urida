package com.urida.comment.controller;

import com.urida.comment.dto.CommentRequestDto;
import com.urida.comment.dto.CommentResponseDto;
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
    public CommentResponseDto createComment(@RequestBody CommentRequestDto CommentRequestDto) {
        Comment comment = commentService.createComment(CommentRequestDto);
        return CommentResponseDto.builder()
                .content(comment.getContent())
                .dateTime(comment.getDateTime())
                .board_id(comment.getBoard().getBoard_id())
                .uid(comment.getUser().getUid())
                .build();
    }

    // 댓글 삭제

    // 댓글 수정
}
