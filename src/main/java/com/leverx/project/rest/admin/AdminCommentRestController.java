package com.leverx.project.rest.admin;

import com.leverx.project.model.Comment;
import com.leverx.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/admin/comments/",
produces = MediaType.APPLICATION_JSON_VALUE,
consumes = MediaType.APPLICATION_JSON_VALUE)
public class AdminCommentRestController {

    private final CommentService commentService;

    @Autowired
    public AdminCommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable("id") Integer id, @RequestBody Comment comment) {
        List<Comment> commentList = commentService.getAll();
        if(commentList.stream().noneMatch(p -> p.getId().equals(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Comment updatedComment = commentService.update(id, comment);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Comment>> getById(@PathVariable("id") Integer id) {
        List<Comment> commentList = commentService.getAll();
        if(commentList.stream().noneMatch(p -> p.getId().equals(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Comment> optionalComment = commentService.getById(id);
        return new ResponseEntity<>(optionalComment, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Comment>> getAll() {
        List<Comment> commentsList = commentService.getAll();
        if(commentsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentsList, HttpStatus.OK);
    }
}
