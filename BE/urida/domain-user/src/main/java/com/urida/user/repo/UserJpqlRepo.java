package com.urida.user.repo;

import com.urida.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserJpqlRepo {

    @PersistenceContext
    private final EntityManager em;

    public Optional<User> findBySocialId(String type, String id) {
        List<User> user = em.createQuery(
                        "SELECT u FROM User u WHERE social_id = :id and type = :type", User.class)
                .setParameter("id", id)
                .setParameter("type", type)
                .getResultList();
        return user.stream().findAny();
    }

    public void saveUser(User user) {
        em.createNativeQuery("insert into user(social_id,language,nickname,type) " +
                        "values(:social_id,:language,:nickname,:type)")
                .setParameter("social_id", user.getSocial_id())
                .setParameter("language", user.getLanguage())
                .setParameter("nickname", user.getNickname())
                .setParameter("type", user.getType())
                .executeUpdate();

    }

    public Optional<User> checkNickname(String nickname) {
        List<User> user = em.createQuery("select u from User u where nickname = :nickname", User.class)
                .setParameter("nickname",nickname)
                .getResultList();

        return user.stream().findAny();
    }

    public Optional<User> findByUid(Long uid) {
        List<User> user = em.createQuery("select u from User u where uid = :uid", User.class)
                .setParameter("uid",uid)
                .getResultList();

        return user.stream().findAny();
    }

    public Optional<User> findByNickname(String nickname) {
        List<User> user = em.createQuery("select u from User u where nickname = :nickname", User.class)
                .setParameter("nickname",nickname)
                .getResultList();

        return user.stream().findAny();
    }
}
