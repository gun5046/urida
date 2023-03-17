package com.urida.likeboard.repo;

import com.urida.likeboard.entity.likeboard;
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
    public Optional<likeboard> findByUserAndBoard(Long uid, Long board_id) {
        List<likeboard> likeBoard = em.createQuery(
                        "select l from likeboard l where l.board.board_id = :board_id and l.user.uid = :uid", likeboard.class
                ).setParameter("board_id", board_id)
                .setParameter("uid", uid)
                .getResultList();

        return likeBoard.stream().findAny();
    }

    public void saveLikeBoard(likeboard likeBoard) {
       em.persist(likeBoard);
    }

    public void deleteLikeBoard(likeboard likeBoard) {
        em.remove(likeBoard);
    }

}
