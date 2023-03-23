package com.urida.board.service.impl;

import com.urida.board.dto.request.ArticleCreateDto;
import com.urida.board.dto.request.ArticleUpdateDto;
import com.urida.board.dto.response.BoardDetailDto;
import com.urida.board.dto.response.BoardListDto;
import com.urida.board.service.BoardService;
import com.urida.board.entity.Board;
import com.urida.comment.dto.CommentResponseDto;
import com.urida.comment.repo.CommentJpqlRepo;
import com.urida.comment.service.CommentService;
import com.urida.exception.NoDataException;
import com.urida.exception.SaveException;
import com.urida.board.repo.BoardJpqlRepo;
import com.urida.likeboard.entity.Likeboard;
import com.urida.likeboard.repo.LikeBoardJpqlRepo;
import com.urida.user.entity.User;
import com.urida.user.repo.UserJpqlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardJpqlRepo boardJpqlRepo;
    private final LikeBoardJpqlRepo likeBoardJpqlRepo;
    private final UserJpqlRepo userJpqlRepo;
    private final CommentJpqlRepo commentJpqlRepo;
    private final CommentService commentService;


    @Override
    @Transactional(readOnly = true)
    public List<BoardListDto> getArticles() {
        List<Board> allArticles = boardJpqlRepo.findAll();
        List<BoardListDto> articleDtoList = new ArrayList<>();

        for (Board article : allArticles) {
            BoardListDto dto = BoardListDto.builder()
                    .board_id(article.getBoard_id())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .view(article.getView())
                    .dateTime(article.getTime())
//                    .assessment(article.getAssessment())
                    .nickname(article.getUser().getNickname())
                    .build();
            articleDtoList.add(dto);
        }

        return articleDtoList;
    }

    @Override
    public BoardDetailDto getArticle(Long id) {
        Board article = boardJpqlRepo.findById(id);
        String nickname = article.getUser().getNickname();
        List<CommentResponseDto> comments = commentService.getComments(id);

        return BoardDetailDto.builder()
                .board_id(article.getBoard_id())
                .title(article.getTitle())
                .content(article.getContent())
                .view(article.getView())
                .dateTime(article.getTime())
//                .assessment(article.getAssessment())
                .nickname(nickname)
                .comment(comments)
                .build();
        /*return boardJpqlRepo.findById(id);*/
    }

    @Override
    public Board createArticle(ArticleCreateDto articleCreateDto) {
        Optional<User> user = userJpqlRepo.findByUid(articleCreateDto.getUid());
        if (user.isPresent()) {
            Board article = Board.builder()
                    .title(articleCreateDto.getTitle())
                    .content(articleCreateDto.getContent())
                    .time(LocalDateTime.now().toString())
                    .user(user.get())
                    .build();

            Likeboard likeboard = Likeboard.builder()
                    .user(user.get())
                    .status(false)
                    .board(article)
                    .build();

            try {
                boardJpqlRepo.saveArticle(article);
                likeBoardJpqlRepo.saveInitialLikeBoard(likeboard);
                return article;
            } catch (Exception e) {
                System.out.println(e.toString());
                throw new SaveException("Value invalid");
            }
        } else {
            throw new NoDataException("Value invalid");
        }
    }

    @Override
    public Board updateArticle(ArticleUpdateDto articleUpdateDto, Long id) {
        Board targetArticle = boardJpqlRepo.findById(id);
        Optional<User> currUser = userJpqlRepo.findByUid(targetArticle.getUser().getUid());
        String newContent = articleUpdateDto.getContent();

        if (currUser.isPresent()) {
            Board updatedArticle = Board.builder()
                    .board_id(targetArticle.getBoard_id())
                    .title(targetArticle.getTitle())
                    .content(newContent)
                    .time(LocalDateTime.now().toString())
                    .user(currUser.get())
                    .comment(targetArticle.getComment())
                    .build();

            try {
                boardJpqlRepo.saveArticle(updatedArticle);
                return updatedArticle;
            } catch (Exception e) {
                throw new SaveException("Value invalid");
            }
        } else {
            throw new NoDataException("Value invalid");
        }
    }

    @Override
    public void deleteArticle(Long id) {
        boardJpqlRepo.removeArticle(id);
    }

    @Override
    public Boolean clickLikeArticle(Long board_id, Long uid, Boolean status) {
        Optional<Likeboard> targetArticle = likeBoardJpqlRepo.findByUserAndBoard(uid, board_id);
        if (targetArticle.isPresent()) {
            likeBoardJpqlRepo.updateLikeBoard(board_id, uid, !status);
        } else {
            likeBoardJpqlRepo.saveLikeBoard(uid, board_id);
        }

        return likeBoardJpqlRepo.findByUserAndBoard(uid, board_id).get().isStatus();
    }

    @Override
    public int likeCnt(Long board_id) {
        likeBoardJpqlRepo.
    }


//    @Override
//    public int likeArticle(Long id) {
//        return likeBoardJpqlRepo.saveLikeBoard(id);
//    }

    /*@Override
    public int dislikeArticle(Long id) {
        return boardJpqlRepo.dislikeArticle(id);
    }*/
}
