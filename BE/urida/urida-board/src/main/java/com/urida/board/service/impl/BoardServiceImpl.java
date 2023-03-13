package com.urida.board.service.impl;

import com.urida.board.dto.ArticleRequestDto;
import com.urida.board.service.BoardService;
import com.urida.entity.Board;
import com.urida.exception.SaveException;
import com.urida.repo.BoardJpqlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardJpqlRepo boardJpqlRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Board> getArticles() {
        return boardJpqlRepo.findAll();
    }

    @Override
    public Board createArticle(ArticleRequestDto articleRequestDto) {

        Board article = Board.builder()
                // writer 정보는 어떻게 넘기나
                .title(articleRequestDto.getTitle())
                .content(articleRequestDto.getContent())
                .time(LocalDateTime.now())
                .build();

        try {
            boardJpqlRepo.saveArticle(article);
            return article;
        } catch(Exception e) {
            throw new SaveException("Value invalid");
        }
    }

    @Override
    public Board updateArticle(ArticleRequestDto articleRequestDto, Long id) {
        Board targetArticle = boardJpqlRepo.findById(id);
        String content = articleRequestDto.getContent();
        targetArticle.
    }

    @Override
    public void deleteArticle(Long id) {
        boardJpqlRepo.removeArticle(id);
    }
}
