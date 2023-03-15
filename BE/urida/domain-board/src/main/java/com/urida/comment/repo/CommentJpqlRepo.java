package com.urida.comment.repo;

import com.urida.board.entity.Board;

import com.urida.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentJpqlRepo {

    private final EntityManager em;

    // 댓글 저장
    public void saveComment(Comment comment) {
        if (comment.getComment_id() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }

    // 해당 게시글 댓글 리스트 출력
    public List<Comment> getComments(Long board_id) {
        return em.createQuery("select c from Comment c where c.board.board_id = :boardId", Comment.class)
                .setParameter("boardId", board_id)
                .getResultList();
    }

    // 특정 댓글 검색
    public Comment findById(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        em.remove(findById(commentId));
    }
}
