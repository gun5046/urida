package com.urida.comment.controller;

import com.urida.comment.dto.CommentRequestDto;
import com.urida.comment.dto.CommentResponseDto;
import com.urida.comment.entity.Comment;
import com.urida.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                .comment_id(comment.getComment_id())
                .content(comment.getContent())
                .dateTime(comment.getDateTime())
                .board_id(comment.getBoard().getBoard_id())
                .uid(comment.getUser().getUid())
                .build();
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public Long deleteComment(@PathVariable Long id) {
        return commentService.removeComment(id);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        Comment updatedComment = commentService.updateComment(id, commentRequestDto);
        return CommentResponseDto.builder()
                .comment_id(updatedComment.getComment_id())
                .content(updatedComment.getContent())
                .dateTime(updatedComment.getDateTime())
                .board_id(updatedComment.getBoard().getBoard_id())
                .uid(updatedComment.getUser().getUid())
                .build();
    }

    // 특정 게시물 댓글
    @GetMapping("/{board_id}")
    public List<CommentResponseDto> commentLists(@PathVariable Long board_id) {
        return commentService.getComments(board_id);
    }
}
