package com.urida.board.service.impl;

import com.urida.board.dto.ArticleRequestDto;
import com.urida.board.dto.ArticleUpdateDto;
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
    @Transactional(readOnly = true)
    public Board getArticle(Long id) {
        return boardJpqlRepo.findById(id);
    }

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
        }else{
            throw new NoDataException("Value invalid");
        }
    }

    @Override
    public Board updateArticle(ArticleUpdateDto articleUpdateDto, Long id) {
        Board targetArticle = boardJpqlRepo.findById(id);
        String content = articleUpdateDto.getContent();
        Board updatedArticle = Board.builder()
                .title(targetArticle.getTitle())
                .content(content)
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .user(targetArticle.getUser())
                .build();

        try {
            boardJpqlRepo.saveArticle(updatedArticle);
            return updatedArticle;
        } catch (Exception e) {
            throw new SaveException("Value invalid");
        }
    }

   @Override
    public void deleteArticle(Long id) {
       boardJpqlRepo.removeArticle(id);
    }
}
