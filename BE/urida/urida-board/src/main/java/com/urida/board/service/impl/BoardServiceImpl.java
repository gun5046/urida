package com.urida.board.service.impl;

import com.urida.board.dto.ArticleRequestDto;
import com.urida.board.dto.ArticleUpdateDto;
import com.urida.board.dto.BoardDto;
import com.urida.board.service.BoardService;
import com.urida.board.entity.Board;
import com.urida.exception.NoDataException;
import com.urida.exception.SaveException;
import com.urida.board.repo.BoardJpqlRepo;
import com.urida.user.entity.User;
import com.urida.user.repo.UserJpqlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardJpqlRepo boardJpqlRepo;
    private final UserJpqlRepo userJpqlRepo;


    @Override
    @Transactional(readOnly = true)
    public List<Board> getArticles() {
        return boardJpqlRepo.findAll();
    }

    @Override
    public BoardDto getArticle(Long id) {
        return boardJpqlRepo.findById(id);
    }

   /* @Override
    public void increaseView(Long id) {
        BoardDto targetArticle = getArticle(id);
        Optional<User> currUser = userJpqlRepo.findByUid(targetArticle.getUid());
        int newCount = targetArticle.getView() + 1;

        Board article = Board.builder()
                .board_id(targetArticle.getBoard_id())
                .title(targetArticle.getTitle())
                .content(targetArticle.getContent())
                .view(newCount)
                .time(targetArticle.getDateTime())
                .user(currUser.get())
                .build();
        boardJpqlRepo.saveArticle(article);
    }
*/

    @Override
    public Board createArticle(ArticleRequestDto articleRequestDto) {
        Optional<User> user = userJpqlRepo.findByUid(articleRequestDto.getUid());
        if(user.isPresent()) {
            Board article = Board.builder()
                    .title(articleRequestDto.getTitle())
                    .content(articleRequestDto.getContent())
                    .time(LocalDateTime.now().toString())
                    .user(user.get())
                    .build();

            try {
                boardJpqlRepo.saveArticle(article);
                return article;
            } catch (Exception e) {
                System.out.println(e.toString());
                throw new SaveException("Value invalid");
            }
        } else{
            throw new NoDataException("Value invalid");
        }
    }

    @Override
    public Board updateArticle(ArticleUpdateDto articleUpdateDto, Long id) {
        BoardDto targetArticle = boardJpqlRepo.findById(id);
        Optional<User> currUser = userJpqlRepo.findByUid(targetArticle.getUid());
        String newContent = articleUpdateDto.getContent();

        if(currUser.isPresent()) {
            Board updatedArticle = Board.builder()
                    .board_id(targetArticle.getBoard_id())
                    .title(targetArticle.getTitle())
                    .content(newContent)
                    .time(LocalDateTime.now().toString())
                    .user(currUser.get())
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
    public int likeArticle(Long id) {
        return boardJpqlRepo.likeArticle(id);
    }

    @Override
    public int dislikeArticle(Long id) {
        return boardJpqlRepo.dislikeArticle(id);
    }
}
