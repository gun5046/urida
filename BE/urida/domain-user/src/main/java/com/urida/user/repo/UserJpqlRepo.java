package com.urida.user.repo;

import com.urida.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserJpqlRepo {

    private final EntityManager em;

    public User findBySocialId(String type, String id){
        return em.createQuery(
                "SELECT u FROM User u WHERE type = :type and social_id = :id", User.class)
                .setParameter("type", type)
                .setParameter("social_id",id)
                .getSingleResult();
    }
}
