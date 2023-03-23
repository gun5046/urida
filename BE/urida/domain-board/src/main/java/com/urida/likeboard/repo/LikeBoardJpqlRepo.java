package com.urida.likeboard.repo;

import com.urida.likeboard.entity.Likeboard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikeBoardJpqlRepo {
    private final EntityManager em;

    // 유저 및 게시글로 좋아요 검색
    public Optional<Likeboard> findByUserAndBoard(Long uid, Long board_id) {
        List<Likeboard> likeBoard = em.createQuery(
                        "select l from Likeboard l where l.board.board_id = :board_id and l.user.uid = :uid", Likeboard.class
                ).setParameter("board_id", board_id)
                .setParameter("uid", uid)
                .getResultList();

        return likeBoard.stream().findAny();
    }

    public void saveInitialLikeBoard(Likeboard likeboard) {
        em.persist(likeboard);
    }
    public void saveLikeBoard(Long uid, Long board_id) {
        em.createNativeQuery("insert into likeboard(uid, board_id, status)" +
                        "values (:uid, :board_id, false)")
                .setParameter("uid", uid)
                .setParameter("board_id", board_id)
                .executeUpdate();
    }

    public Boolean updateLikeBoard(Long board_id, Long uid, boolean status) {
        em.createNativeQuery("update Likeboard set status = :status where board_id=:board_id and uid =:uid")
                .setParameter("board_id",board_id)
                .setParameter("status",status)
                .setParameter("uid", uid)
                .executeUpdate();

        return status;
    }

    public int likeCnt(Long board_id) {
        List<Likeboard> likeboard = em.createQuery(
                        "select l from Likeboard l where l.board.board_id = :board_id", Likeboard.class
                )
                .setParameter("board_id", board_id)
                .getResultList();
        Optional<Likeboard> targetArticle = likeboard.stream().findAny();
    }
/*
    public void deleteLikeBoard(Likeboard likeBoard) {
        em.remove(likeBoard);
    }
*/

}
