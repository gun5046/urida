package com.urida.board.repo;


import com.urida.board.entity.Board;
import com.urida.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardJpqlRepo {

    private final EntityManager em;

    public Board getArticle(Long id) {
        return em.find(Board.class, id);
    }

    // 특정 게시물
    public Board findById(Long boardId) {
            Board board = em.find(Board.class, boardId);
            board.addView();
            return board;
    }

    // 게시글 저장
    public void saveArticle(Board board) {
//        em.createNativeQuery(
//                        "insert into Board(id,title,content,view,time,assessment)" +
//                                "values (:id, :title, :content, :view, :time, :assessment)")
//                .setParameter("id", board.getId())
//                .setParameter("title", board.getTitle())
//                .setParameter("content", board.getContent())
//                .setParameter("view", board.getView())
//                .setParameter("time", board.getTime())
//                .setParameter("assessment", board.getAssessment())
//                .executeUpdate();
        if (board.getBoard_id() == null) {
            em.persist(board);
        } else {
            em.merge(board);
        }

    }

    // 게시글 삭제
    public void removeArticle(Long boardId) {
        Board article = em.find(Board.class, boardId);
        em.remove(article);
    }

    //게시글 전체조회
    public List<Board> findAll(int category_id) {
        return em.createQuery(
                        "select b from Board b where b.category_id = :category_id", Board.class)
                .setParameter("category_id", category_id)
                .getResultList();
    }

    //게시글 제목 검색
    public List<Board> searchByTitle(String title) {
        try {
            return em.createQuery(
                            "select b from Board b where b.title like :title", Board.class)
                    .setParameter("title", "%title%")
                    .getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }
}
