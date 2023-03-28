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

    public void saveLikeBoard(Long board_id, Long uid) {
        em.createNativeQuery("insert into likeboard(uid, board_id, status)" +
                        "values (:uid, :board_id, true)")
                .setParameter("uid", uid)
                .setParameter("board_id", board_id)
                .executeUpdate();
    }

    public int likeCnt(Long board_id) {
        List<Likeboard> likeboard = em.createQuery(
                        "select l from Likeboard l where l.board.board_id = :board_id and l.status = true", Likeboard.class
                )
                .setParameter("board_id", board_id)
                .getResultList();

        return likeboard.size();
    }
    public void deleteLikeBoard(Optional<Likeboard> likeBoard) {
        em.remove(likeBoard.get());
    }
}
