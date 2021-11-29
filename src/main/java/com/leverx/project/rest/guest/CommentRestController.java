package com.leverx.project.rest.guest;

import com.leverx.project.model.Comment;
import com.leverx.project.model.User;
import com.leverx.project.service.CommentService;
import com.leverx.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/guests/",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public class CommentRestController {

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentRestController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Comment> create(@PathVariable("userId") Integer userId, @RequestBody Comment comment) {

        List<User> userList = userService.getAll();

        if(userList.stream().noneMatch(p -> p.getId().equals(userId))) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        if(comment == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }

        commentService.create(userId, comment);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUserId(@PathVariable("userId") Integer userId) {

        List<User> userList = userService.getAll();

        if(userList.stream().noneMatch(p -> p.getId().equals(userId))) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        List<Comment> commentList = commentService.getAllCommentsById(userId);

        if(commentList.isEmpty()) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Comment>> getAll() {

        List<Comment> commentsList = commentService.getAll();

        if(commentsList.isEmpty()) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        return new ResponseEntity<>(commentsList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {

        List<Comment> commentList = commentService.getAll();

        if(commentList.stream().noneMatch(p -> p.getId().equals(id))) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        commentService.delete(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Comment>> getById(@PathVariable("id") Integer id) {

        List<Comment> commentList = commentService.getAll();

        if(commentList.stream().noneMatch(p -> p.getId().equals(id))) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        Optional<Comment> optionalComment = commentService.getById(id);

        return new ResponseEntity<>(optionalComment, HttpStatus.OK);
    }
}
