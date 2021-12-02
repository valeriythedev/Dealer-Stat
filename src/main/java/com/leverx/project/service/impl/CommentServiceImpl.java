package com.leverx.project.service.impl;

import com.leverx.project.dto.CommentDTO;
import com.leverx.project.model.Comment;
import com.leverx.project.model.User;
import com.leverx.project.repository.CommentDAO;
import com.leverx.project.service.CommentService;
import com.leverx.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO, UserService userService) {
        this.commentDAO = commentDAO;
        this.userService = userService;
    }

    @Override
    public Comment create(Integer id, CommentDTO commentDTO) {
        Comment comment = commentDTO.toComment(commentDTO);

        Optional<User> optionalUser = userService.getById(id);
        List<User> userList = new ArrayList<>();
        userList.add(optionalUser.get());
        comment.setUsers(userList);

        Optional<User> optionalAuthor = userService.getById(commentDTO.getAuthor_id());
        List<User> authorList = new ArrayList<>();
        authorList.add(optionalAuthor.get());
        comment.setAuthorsList(authorList);

        comment.setCreated_at(LocalDateTime.now());
        log.info("IN CommentServiceImpl create() user id {}, comment {}", id, comment);
        return commentDAO.save(comment);
    }

    @Override
    public Comment update(Integer id,Comment comment) {
        Optional<Comment> optionalComment = commentDAO.findById(id);
        optionalComment.get().setApproved(comment.isApproved());
        log.info("IN CommentServiceImpl update() comment with id {}, {}", id, optionalComment);
        return commentDAO.save(optionalComment.get());
    }

    @Override
    public Optional<Comment> getById(Integer id) {
        Optional<Comment> optionalComment = commentDAO.findById(id);
        log.info("IN CommentServiceImpl getById() id {}, comment {}", id, optionalComment.get());
        return optionalComment;
    }

    @Override
    public void delete(Integer id) {
        log.info("IN CommentServiceImpl delete() comment with id {}", id);
        commentDAO.deleteById(id);
    }

    @Override
    public List<Comment> getAllCommentsById(Integer id) {
        List<Comment> commentsList = commentDAO.findAllCommentsById(id);
        log.info("IN CommentServiceImpl getAllCommentsById() user id {}", id);
        return commentsList;
    }

    @Override
    public List<Comment> getAllCommentsByAuthorId(Integer authorId) {
        List<Comment> commentsList = commentDAO.findAllCommentsByAuthorId(authorId);
        log.info("IN CommentServiceImpl getAllCommentsByAuthorId() id {}", authorId);
        return commentsList;
    }

    @Override
    public List<Comment> getAll() {
        log.info("IN CommentServiceImpl getAll() comments List");
        return commentDAO.findAll();
    }
}
