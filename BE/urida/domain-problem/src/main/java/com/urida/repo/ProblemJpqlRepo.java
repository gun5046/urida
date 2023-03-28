package com.urida.repo;

import com.urida.entity.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProblemJpqlRepo{

    private final EntityManager em;

    public Optional<Problem> findByProId(Long proId) {
        List<Problem> problem = em.createQuery(
                "SELECT p FROM Problem  p WHERE pro_id = :proId", Problem.class)
                .setParameter("proId", proId)
                .getResultList();

        return problem.stream().findAny();
    }

    public void saveProblem(Problem problem) {
//        em.createNativeQuery("insert into problem(pro_id,sentence_id, answer_id, type, category_id, wrong_cnt, uid, choices, examples) " +
//                        "values(:pro_id,:sentence_id,:answer_id,:type, :category_id, :wrong_cnt, :uid, :choices, :examples)")
//                .setParameter("pro_id", problem.getPro_id())
//                .setParameter("sentence_id", problem.getSentence_id())
//                .setParameter("answer_id", problem.getAnswer_id())
//                .setParameter("type", problem.getType())
//                .setParameter("category_id", problem.getCategory_id())
//                .setParameter("wrong_cnt", problem.getWrong_cnt())
//                .setParameter("uid", problem.getUser())
//                .setParameter("choices", problem.getChoices())
//                .setParameter("examples", problem.getExamples())
//                .executeUpdate();
        em.persist(problem);
        em.flush();
    }


    public List<Problem> findByUserId(Long userId) {
        return em.createQuery(
                        "SELECT p FROM Problem  p Inner Join p.user u WHERE u.id = :userId", Problem.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void deleteProInList(Long proId) {

        if(findByProId(proId).isPresent()){
            Problem deleteProblem = findByProId(proId).get();
            em.remove(deleteProblem);
            em.flush();
        }

//        em.createQuery("DELETE FROM Problem p where p.user.id=:userId and p.pro_id=:proId")
//                .setParameter("userId",userId)
//                .setParameter("proId",proId)
//                .executeUpdate();
    }

    public void updateProblem(Long proId) {
        em.createQuery("update Problem p set p.wrong_cnt = p.wrong_cnt + 1 where p.id = :proId")
                .setParameter("proId", proId)
                .executeUpdate();
        em.clear();
    }
}
