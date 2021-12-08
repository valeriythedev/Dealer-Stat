package com.leverx.project.rest.trader;

import com.leverx.project.dto.GameObjectDTO;
import com.leverx.project.model.Comment;
import com.leverx.project.model.Game;
import com.leverx.project.model.GameObject;
import com.leverx.project.model.User;
import com.leverx.project.security.jwt.JwtTokenProvider;
import com.leverx.project.service.CommentService;
import com.leverx.project.service.GameObjectService;
import com.leverx.project.service.GameService;
import com.leverx.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/traders/",
produces = MediaType.APPLICATION_JSON_VALUE,
consumes = MediaType.APPLICATION_JSON_VALUE)
public class TraderRestController {

    private final UserService userService;
    private final GameObjectService gameObjectService;
    private final GameService gameService;
    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public TraderRestController(UserService userService, GameObjectService gameObjectService, GameService gameService, CommentService commentService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.gameObjectService = gameObjectService;
        this.gameService = gameService;
        this.commentService = commentService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PatchMapping("/forgot")
    public ResponseEntity<User> updatePassword(@RequestHeader(value = "AUTHORIZATION") String bearerToken, @RequestBody User user) {
        String token = bearerToken.substring(7, bearerToken.length());
        User userFromToken = userService.getByEmail(jwtTokenProvider.getEmail(token));
        if(userFromToken == null || user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User updatedUser = userService.update(userFromToken.getId(), user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/objects/")
    public ResponseEntity<GameObject> create(@RequestHeader(value = "AUTHORIZATION") String bearerToken, @RequestBody GameObjectDTO gameObjectDTO) {
        if(gameObjectDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Game> gameList = gameService.getAll();
        if(gameList.stream().noneMatch(p -> p.getId().equals(gameObjectDTO.getGame_id()))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String token = bearerToken.substring(7, bearerToken.length());
        User user = userService.getByEmail(jwtTokenProvider.getEmail(token));
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        gameObjectDTO.setAuthor_id(user.getId());
        GameObject gameObject = gameObjectService.create(gameObjectDTO);
        return new ResponseEntity<>(gameObject, HttpStatus.OK);
    }

    @PatchMapping("/objects/{id}")
    public ResponseEntity<GameObject> update(@PathVariable("id") Integer id,@RequestHeader(value = "AUTHORIZATION") String bearerToken, @RequestBody GameObject gameObject) {
        String token = bearerToken.substring(7, bearerToken.length());
        User user = userService.getByEmail(jwtTokenProvider.getEmail(token));
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<GameObject> gameObjectList = gameObjectService.getAllGameObjectsById(user.getId());
        if(gameObjectList.stream().noneMatch(p -> p.getId().equals(id))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        GameObject updatedGameObject = gameObjectService.update(id, gameObject);
        return new ResponseEntity<>(updatedGameObject, HttpStatus.OK);
    }

    @DeleteMapping("/objects/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id, @RequestHeader(value = "AUTHORIZATION") String bearerToken) {
        String token = bearerToken.substring(7, bearerToken.length());
        User user = userService.getByEmail(jwtTokenProvider.getEmail(token));
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<GameObject> gameObjectList = gameObjectService.getAllGameObjectsById(user.getId());
        if(gameObjectList.stream().noneMatch(p -> p.getId().equals(id))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        gameObjectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/objects/my")
    public ResponseEntity<List<GameObject>> getAllGameObjectsById(@RequestHeader(value = "AUTHORIZATION") String bearerToken) {
        String token = bearerToken.substring(7, bearerToken.length());
        User user = userService.getByEmail(jwtTokenProvider.getEmail(token));
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<GameObject> gameObjectList = gameObjectService.getAllGameObjectsById(user.getId());
        if(gameObjectList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gameObjectList, HttpStatus.OK);
    }

    @GetMapping("/comments/my")
    public ResponseEntity<List<Comment>> getAllCommentsById(@RequestHeader(value = "AUTHORIZATION") String bearerToken) {
        String token = bearerToken.substring(7, bearerToken.length());
        User user = userService.getByEmail(jwtTokenProvider.getEmail(token));
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Comment> commentList = commentService.getAllCommentsById(user.getId());
        if(commentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @GetMapping("/games/")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> gameList = gameService.getAll();
        if(gameList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }
}
