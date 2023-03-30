package com.urida.board.service.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.urida.board.dto.request.ArticleCreateDto;
import com.urida.board.dto.request.ArticleUpdateDto;
import com.urida.board.dto.response.BoardDetailDto;
import com.urida.board.dto.response.BoardListDto;
import com.urida.board.service.BoardService;
import com.urida.board.entity.Board;
import com.urida.comment.dto.CommentResponseDto;
import com.urida.comment.service.CommentService;
import com.urida.exception.NoDataException;
import com.urida.exception.SaveException;
import com.urida.board.repo.BoardJpqlRepo;
import com.urida.likeboard.entity.Likeboard;
import com.urida.likeboard.repo.LikeBoardJpqlRepo;
import com.urida.user.entity.User;
import com.urida.user.repo.UserJpqlRepo;
import io.grpc.Context;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardJpqlRepo boardJpqlRepo;
    private final LikeBoardJpqlRepo likeBoardJpqlRepo;
    private final UserJpqlRepo userJpqlRepo;
    private final CommentService commentService;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String drawingStorage;

    private final Storage storage;

    private List<BoardListDto> getBoardListDtos(List<Board> userArticles) {
        List<BoardListDto> articleDtoList = new ArrayList<>();
        for(Board article : userArticles) {
            BoardListDto dto = BoardListDto.builder()
                    .board_id(article.getBoard_id())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .view(article.getView())
                    .dateTime(article.getTime())
                    .category_id(article.getCategory_id())
                    .likeCnt(likeBoardJpqlRepo.likeCnt(article.getBoard_id()))
                    .commentCnt(commentService.commentCnt(article.getBoard_id()))
                    .nickname(article.getUser().getNickname())
                    .build();

            articleDtoList.add(dto);
        }
        return articleDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardListDto> getArticles(int category_id) {
        List<Board> allArticles = boardJpqlRepo.findAll(category_id);
        return getBoardListDtos(allArticles);

    }

    @Override
    @Transactional(readOnly = true)
    public BoardDetailDto getArticle(Long id) {

        Board article = boardJpqlRepo.findById(id);
        if(article == null){
            throw new NoDataException("invalid data(Not Found)");
        }
        article.addView();
        String nickname = article.getUser().getNickname();
        List<CommentResponseDto> comments = commentService.getComments(id);

        return BoardDetailDto.builder()
                .board_id(article.getBoard_id())
                .title(article.getTitle())
                .content(article.getContent())
                .image(article.getImage())
                .view(article.getView())
                .dateTime(article.getTime())
                .likeCnt(likeBoardJpqlRepo.likeCnt(article.getBoard_id()))
                .commentCnt(comments.size())
                .nickname(nickname)
                .comment(comments)
                .build();
        /*return boardJpqlRepo.findById(id);*/
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardListDto> getArticlesByUser(Long uid, int category_id) {
        List<Board> userArticles = boardJpqlRepo.findByUid(uid, category_id);
        return getBoardListDtos(userArticles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardListDto> getArticleByUserCommentedOn(Long uid, int category_id) {
        List<Board> userCommentedArticles = boardJpqlRepo.findByUserCommentedOn(uid, category_id);
        return getBoardListDtos(userCommentedArticles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardListDto> getLiked(Long uid, int category_id) {
        List<Board> userLikedArticles = boardJpqlRepo.findByLiked(uid, category_id);
        return getBoardListDtos(userLikedArticles);
    }

    @Override
    public Board createArticle(ArticleCreateDto articleCreateDto) throws IOException {
        Optional<User> user = userJpqlRepo.findByUid(articleCreateDto.getUid());

        if(articleCreateDto.getImage() == null) {
            if (user.isPresent()) {
                Board article = Board.builder()
                        .title(articleCreateDto.getTitle())
                        .content(articleCreateDto.getContent())
                        .image(null)
                        .category_id(articleCreateDto.getCategory_id())
                        .time(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .user(user.get())
                        .build();
                System.out.println(ZonedDateTime.now());
                System.out.println(ZonedDateTime.now(ZoneId.of("Asia/Seoul")));
                try {
                    boardJpqlRepo.saveArticle(article);
                    return article;
                } catch (Exception e) {
                    System.out.println(e.toString());
                    throw new SaveException("Value invalid");
                }
            } else {
                throw new NoDataException("Value invalid");
            }

        }
        String uuid = UUID.randomUUID().toString(); // GCS에 저장될 파일 이름
        String type = articleCreateDto.getImage().getContentType(); // 파일 형식

        // cloud 이미지 업로드
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(drawingStorage, uuid)
                        .setContentType(type)
                        .build(),
                articleCreateDto.getImage().getInputStream()
        );

        if (user.isPresent()) {
            Board article = Board.builder()
                    .title(articleCreateDto.getTitle())
                    .content(articleCreateDto.getContent())
                    .image("https://storage.cloud.google.com/drawing-storage/" + uuid)
                    .category_id(articleCreateDto.getCategory_id())
                    .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .user(user.get())
                    .build();

            try {
                boardJpqlRepo.saveArticle(article);
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
                    .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
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
    public Boolean clickLikeArticle(Long board_id, Long uid) {
        Optional<Likeboard> targetArticle = likeBoardJpqlRepo.findByUserAndBoard(uid, board_id);
        if (targetArticle.isPresent()) {
            likeBoardJpqlRepo.deleteLikeBoard(targetArticle);
            System.out.println(boardJpqlRepo.findById(board_id));
            return false;
        } else {
            likeBoardJpqlRepo.saveLikeBoard(board_id, uid);
            System.out.println(likeBoardJpqlRepo.findByUserAndBoard(uid,board_id).get().isStatus());
            return true;
        }
    }

    @Override
    public int likeCnt(Long board_id) {
        return likeBoardJpqlRepo.likeCnt(board_id);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isLiked(Long board_id, Long uid) {
        Optional<Likeboard> isLiked = likeBoardJpqlRepo.findByUserAndBoard(uid, board_id);
        return isLiked.isPresent();
    }

    /*@Override
    public int dislikeArticle(Long id) {
        return boardJpqlRepo.dislikeArticle(id);
    }*/
}
