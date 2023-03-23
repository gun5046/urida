package com.urida.likeboard.service.impl;

import com.urida.board.entity.Board;
import com.urida.board.repo.BoardJpqlRepo;
import com.urida.exception.NoDataException;
import com.urida.likeboard.entity.likeboard;
import com.urida.likeboard.repo.LikeBoardJpqlRepo;
import com.urida.likeboard.service.LikeBoardService;
import com.urida.user.entity.User;
import com.urida.user.repo.UserJpqlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeBoardServiceImpl implements LikeBoardService {
    private final UserJpqlRepo userJpqlRepo;
    private final BoardJpqlRepo boardJpqlRepo;
    private final LikeBoardJpqlRepo likeBoardJpqlRepo;

    @Override
    public boolean clickLike(Long uid, Long board_id) {
        Optional<User> currUser = userJpqlRepo.findByUid(uid);
        Board targetArticle = boardJpqlRepo.getArticle(board_id);

        if (currUser.isPresent()) {
            Optional<likeboard> existingLikeBoard = likeBoardJpqlRepo.findByUserAndBoard(uid, board_id);
            if (existingLikeBoard.isPresent()) {
                likeBoardJpqlRepo.deleteLikeBoard(existingLikeBoard.get());
                targetArticle.dislike();
                return false;
            } else {
                likeboard likeBoard = likeboard.builder()
                        .board(targetArticle)
                        .user(currUser.get())
                        .build();

                likeBoardJpqlRepo.saveLikeBoard(likeBoard);
                targetArticle.like();
                return true;
            }
        } else {
            throw new NoDataException("No user Found");
        }
    }
}
