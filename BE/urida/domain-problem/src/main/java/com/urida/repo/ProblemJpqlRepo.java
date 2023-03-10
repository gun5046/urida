package com.urida.repo;

import com.urida.entity.Problem;
import com.urida.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProblemJpqlRepo {

    private final EntityManager em;

    public Optional<Problem> findByProId(Long proId) {
        List<Problem> problem = em.createQuery(
                "SELECT p FROM Problem  p WHERE proId = :proId", Problem.class)
                .setParameter("proId", proId)
                .getResultList();

        return problem.stream().findAny();
    }

    public void saveProblem(Problem problem) {
        em.createNativeQuery("insert into problem(pro_id,sentence_id, answer_id, type, categoryId, wrong_cnt, uid) " +
                        "values(:pro_id,:sentence_id,:answer_id,:type, :categoryId, :wrong_cnt, :uid)")
                .setParameter("pro_id", problem.getProId())
                .setParameter("sentence_id", problem.getSentenceId())
                .setParameter("answer_id", problem.getAnswerId())
                .setParameter("type", problem.getType())
                .setParameter("categoryId", problem.getCategoryId())
                .setParameter("wrong_cnt", problem.getWrongCnt())
                .setParameter("uid", problem.getUser())
                .executeUpdate();
    }


    public List<Problem> findByUserId(Long userId) {
        List<Problem> problems = em.createQuery(
                        "SELECT p FROM Problem  p Inner Join p.user u WHERE u.id = :userId", Problem.class)
                .setParameter("userId", userId)
                .getResultList();

        return problems;
    }
}
