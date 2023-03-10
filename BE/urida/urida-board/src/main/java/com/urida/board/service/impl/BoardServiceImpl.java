package com.urida.board.service.impl;

import com.urida.board.dto.ArticleDto;
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
    public Board createArticle(ArticleDto articleDto) {

        Board article = Board.builder()
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
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
    public Board updateArticle(ArticleDto articleDto, Long id) {
        return null;
    }

    @Override
    public void deleteArticle(Long id) {

    }
}
