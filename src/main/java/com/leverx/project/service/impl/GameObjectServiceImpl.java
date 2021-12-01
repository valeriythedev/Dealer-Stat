package com.leverx.project.service.impl;

import com.leverx.project.dto.GameObjectDTO;
import com.leverx.project.model.Game;
import com.leverx.project.model.GameObject;
import com.leverx.project.model.User;
import com.leverx.project.repository.GameObjectDAO;
import com.leverx.project.service.GameObjectService;
import com.leverx.project.service.GameService;
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
public class GameObjectServiceImpl implements GameObjectService {

    private final GameObjectDAO gameObjectDAO;
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public GameObjectServiceImpl(GameObjectDAO gameObjectDAO, UserService userService, GameService gameService) {
        this.gameObjectDAO = gameObjectDAO;
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public GameObject create(GameObjectDTO gameObjectDTO) {
        GameObject gameObject = gameObjectDTO.toGameObject(gameObjectDTO);

        Integer author_id = gameObjectDTO.getAuthor_id();
        Optional<User> optionalUser = userService.getById(author_id);
        List<User> userList = new ArrayList<>();
        userList.add(optionalUser.get());
        gameObject.setUsers(userList);

        Integer game_id = gameObjectDTO.getGame_id();
        Optional<Game> optionalGame = gameService.getById(game_id);
        List<Game> gameList = new ArrayList<>();
        gameList.add(optionalGame.get());
        gameObject.setGames(gameList);

        gameObject.setCreated_at(LocalDateTime.now());

        log.info("IN GameObjectServiceImpl create() gameObject {}", gameObject);
        return gameObjectDAO.save(gameObject);
    }

    @Override
    public GameObject update(Integer id, GameObject gameObject) {
        Optional<GameObject> optionalGameObject = gameObjectDAO.findById(id);
        optionalGameObject.get().setTitle(gameObject.getTitle());
        optionalGameObject.get().setUpdated_at(LocalDateTime.now());
        log.info("IN GameObjectServiceImpl update() gameObject with id {}, {}", id, gameObject);
        return gameObjectDAO.save(optionalGameObject.get());
    }

    @Override
    public void delete(Integer id) {
        log.info("IN GameObjectServiceImpl delete() gameObject with id {}", id);
        gameObjectDAO.deleteById(id);
    }

    @Override
    public List<GameObject> getAllGameObjectsById(Integer id) {
        List<GameObject> gameObjectList = gameObjectDAO.findAllGameObjectsById(id);
        log.info("IN CommentServiceImpl getAllGameObjectsById() user id {}", id);
        return gameObjectList;
    }

    @Override
    public List<GameObject> getAll() {
        log.info("IN GameObjectServiceImpl getAll() gameObjects");
        return gameObjectDAO.findAll();
    }
}
