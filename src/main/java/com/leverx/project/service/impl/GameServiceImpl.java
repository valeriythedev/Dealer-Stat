package com.leverx.project.service.impl;

import com.leverx.project.model.Game;
import com.leverx.project.repository.GameDAO;
import com.leverx.project.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameDAO gameDAO;

    @Autowired
    public GameServiceImpl(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @Override
    public Game create(Game game) {
        log.info("IN GameServiceImpl create() game {}", game);
        return gameDAO.save(game);
    }

    @Override
    public Game update(Integer id, Game game) {
        Optional<Game> updatedGame = gameDAO.findById(id);
        updatedGame.get().setName(game.getName());
        log.info("IN GameServiceImpl update() game with id {}, {}", id, game);
        return gameDAO.save(updatedGame.get());
    }

    @Override
    public Optional<Game> getById(Integer id) {
        Optional<Game> optionalGame = gameDAO.findById(id);
        log.info("IN GameServiceImpl getById() id {}, game {}", id, optionalGame);
        return optionalGame;
    }

    @Override
    public void delete(Integer id) {
        gameDAO.deleteById(id);
        log.info("IN GameServiceImpl delete() game with id {}", id);
    }

    @Override
    public List<Game> getAll() {
        log.info("IN GameServiceImpl getAll() games List");
        return gameDAO.findAll();
    }
}
