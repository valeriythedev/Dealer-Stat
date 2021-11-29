package com.leverx.project.service;

import com.leverx.project.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment create(Integer userId, Comment comment);

    Comment update(Integer id,Comment comment);

    Optional<Comment> getById(Integer id);

    void delete(Integer id);

    List<Comment> getAllCommentsById(Integer id);

    List<Comment> getAll();
}
