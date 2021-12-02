package com.leverx.project.repository;

import com.leverx.project.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Integer> {

    @Query(value = "select c FROM Comment c JOIN c.users u WHERE u.id = :userId" )
    List<Comment> findAllCommentsById(Integer userId);

    @Query(value = "select c FROM Comment c JOIN c.authorsList u WHERE u.id = :authorId")
    List<Comment> findAllCommentsByAuthorId(Integer authorId);
}
