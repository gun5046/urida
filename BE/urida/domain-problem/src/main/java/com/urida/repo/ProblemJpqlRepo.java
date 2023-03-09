package com.urida.repo;

import com.urida.entity.Problem;
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

    public Optional<Problem> findByUserId(Long proId) {
        List<Problem> problem = em.createQuery(
                        "SELECT p FROM Problem  p WHERE proId = :proId", Problem.class)
                .setParameter("proId", proId)
                .getResultList();

        return problem.stream().findAny();
    }

}
