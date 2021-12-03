package com.leverx.project.rest.guest;

import com.leverx.project.dto.CommentDTO;
import com.leverx.project.model.Comment;
import com.leverx.project.model.User;
import com.leverx.project.security.jwt.JwtTokenProvider;
import com.leverx.project.service.CommentService;
import com.leverx.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/guests/",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public class CommentRestController {

    private final CommentService commentService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public CommentRestController(CommentService commentService, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.commentService = commentService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Optional<Comment>> create(@PathVariable("userId") Integer userId,
                                                    @RequestHeader(value = "AUTHORIZATION") String bearerToken,
                                                    @RequestBody CommentDTO commentDTO) {
        String token = bearerToken.substring(7, bearerToken.length());
        User user = userService.getByEmail(jwtTokenProvider.getEmail(token));
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<User> userList = userService.getAll();
        if(userList.stream().noneMatch(p -> p.getId().equals(userId))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(commentDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        commentDTO.setAuthor_id(user.getId());
        commentService.create(userId, commentDTO);
        return new ResponseEntity(commentDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUserId(@PathVariable("userId") Integer userId) {
        List<User> userList = userService.getAll();
        if(userList.stream().noneMatch(p -> p.getId().equals(userId))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Comment> commentList = commentService.getAllCommentsById(userId);
        if(commentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Comment>> getAllMyComments(@RequestHeader(value = "AUTHORIZATION") String bearerToken) {
        String token = bearerToken.substring(7, bearerToken.length());
        User user = userService.getByEmail(jwtTokenProvider.getEmail(token));
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Comment> commentList = commentService.getAllCommentsByAuthorId(user.getId());
        if(commentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Comment>> getAll() {
        List<Comment> commentsList = commentService.getAll();
        if(commentsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentsList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id,
                                 @RequestHeader(value = "AUTHORIZATION") String bearerToken) {
        String token = bearerToken.substring(7, bearerToken.length());
        User user = userService.getByEmail(jwtTokenProvider.getEmail(token));
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Comment> commentList = commentService.getAllCommentsByAuthorId(user.getId());
        if(commentList.stream().noneMatch(p -> p.getId().equals(id))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
