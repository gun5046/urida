package com.urida.likeboard.repo;

import com.urida.likeboard.entity.LikeBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class LikeBoardJpqlRepo {
    private final EntityManager em;

    public LikeBoard findByUserAndBoard(Long uid, Long board_id) {
        return em.createQuery(
                        "select l from LikeBoard l where l.board.board_id = :board_id and l.user.uid = :uid", LikeBoard.class
                ).setParameter("board_id", board_id)
                .setParameter("uid", uid)
                .getSingleResult();
    }

}
