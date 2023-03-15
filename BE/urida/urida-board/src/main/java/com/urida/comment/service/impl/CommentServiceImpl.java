package com.urida.comment.service.impl;

import com.urida.board.entity.Board;
import com.urida.board.repo.BoardJpqlRepo;
import com.urida.comment.dto.CommentRequestDto;
import com.urida.comment.entity.Comment;
import com.urida.comment.repo.CommentJpqlRepo;
import com.urida.comment.service.CommentService;
import com.urida.exception.NoDataException;
import com.urida.exception.SaveException;
import com.urida.user.entity.User;
import com.urida.user.repo.UserJpqlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserJpqlRepo userJpqlRepo;
    private final BoardJpqlRepo boardJpqlRepo;
    private final CommentJpqlRepo commentJpqlRepo;

    @Override
    public Comment createComment(CommentRequestDto commentRequestDto) {
        Board targetArticle = boardJpqlRepo.getArticle(commentRequestDto.getBoard_id());
        Optional<User> user = userJpqlRepo.findByUid(commentRequestDto.getUid());
        if(user.isPresent()) {
            Comment comment = Comment.builder()
                    .content(commentRequestDto.getContent())
                    .dateTime(LocalDateTime.now().toString())
                    .board(targetArticle)
                    .user(user.get())
                    .build();
            try{
                commentJpqlRepo.saveComment(comment);
                return comment;
            } catch(Exception e) {
                throw new SaveException("Value Invalid");
            }
        } else{
            throw new NoDataException("Value Invalid");
        }
    }

    @Override
    public Comment updateComment(CommentRequestDto commentRequestDto) {
        return null;
    }

    @Override
    public int removeComment(Long comment_id) {
        return 0;
    }
}
