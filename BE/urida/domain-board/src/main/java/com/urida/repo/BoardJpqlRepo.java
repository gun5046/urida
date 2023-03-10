package com.urida.repo;


import com.urida.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardJpqlRepo {

    private final EntityManager em;

    // 특정 게시물
    public Board findById(Long boardId) {
//        List<Board> article = em.createQuery(
//                        "select b from Board b where id = :board_id", Board.class)
//                .setParameter("board_id", board_id)
//                .getResultList();
//        return article.stream().findAny();
        return em.find(Board.class, boardId);
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
        em.persist(board);
    }

    // 게시글 삭제
    public void removeArticle(Long boardId) {
        em.remove(findById(boardId));
    }

    //게시글 전체조회
    public List<Board> findAll() {
        return em.createQuery(
                        "select b from Board b", Board.class)
                .getResultList();
    }

    //게시글 제목 검색
    public List<Board> searchBoard(String title) {
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
