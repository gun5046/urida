package com.urida.board.repo;


import com.urida.board.entity.Board;
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
        return em.find(Board.class, boardId);
    }

    // 유저 작성한 게시글 (카테고리 별)
    public List<Board> findByUid(Long uid, int category_id) {
        return em.createQuery("select b from Board b where b.user.uid = :uid and b.category_id = :category_id order by time desc", Board.class)
                .setParameter("uid", uid)
                .setParameter("category_id", category_id)
                .getResultList();
    }

    // 유저가 좋아요 한 글 (카테고리 별)
    public List<Board> findByLiked(Long uid, int catetory_id) {
        return em.createQuery("select l.board from Likeboard l where l.user.uid = :uid and l.board.category_id = :category_id", Board.class)
                .setParameter("uid", uid)
                .setParameter("category_id", catetory_id)
                .getResultList();
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
                        "select b from Board b where b.category_id = :category_id order by time desc", Board.class)
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
