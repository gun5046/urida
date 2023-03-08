package com.urida.user.repo;

import com.urida.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserJpqlRepo {

    private final EntityManager em;

    public Optional<User> findBySocialId(String type, String id){
        List<User>user = em.createQuery(
                "SELECT u FROM User u WHERE social_id = :id and type = :type", User.class)
                .setParameter("id",id)
                .setParameter("type", type)
                .getResultList();
        return user.stream().findAny();
    }
}
