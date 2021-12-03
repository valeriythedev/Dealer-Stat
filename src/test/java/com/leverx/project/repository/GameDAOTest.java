package com.leverx.project.repository;

import com.leverx.project.application.Application;
import com.leverx.project.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
@Transactional
@Slf4j
public class GameDAOTest {

    private final GameDAO gameDAO;

    @Autowired
    public GameDAOTest(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @Test
    void shouldCreateGame() {
        log.info("Execute test GameDAOTest shouldCreateGame()");
        assertNotNull(gameDAO);
        Game expectedGame = new Game(null, "Dota");
        Game savedGame = gameDAO.save(expectedGame);
        assertNotNull(savedGame);
        expectedGame.setId(savedGame.getId());
        Optional<Game> actualGame = gameDAO.findById(expectedGame.getId());
        assertTrue(actualGame.isPresent());
        assertEquals(expectedGame, actualGame.get());
    }

    @Test
    void shouldUpdateGame() {
        log.info("Execute test GameDAOTest shouldUpdateGame()");
        assertNotNull(gameDAO);
        Game expectedGame = gameDAO.findById(1).get();
        Game actualGame = new Game(1, "CS:GO");
        expectedGame.setName(actualGame.getName());
        gameDAO.save(actualGame);
        actualGame = gameDAO.findById(1).get();
        assertEquals(expectedGame, actualGame);
    }

    @Test
    void shouldFindGameById() {
        log.info("Execute test GameDAOTest shouldFindById()");
        assertNotNull(gameDAO);
        Game expectedGame = new Game(1, "Dota");
        Optional<Game> actualGame = gameDAO.findById(expectedGame.getId());
        assertTrue(actualGame.isPresent());
        assertEquals(expectedGame, actualGame.get());
    }

    @Test
    void shouldFindAll() {
        log.info("Execute test GameDAOTest shouldFindAll()");
        assertNotNull(gameDAO);
        List<Game> gameList = gameDAO.findAll();
        assertNotNull(gameList);
    }

    @Test
    void shouldDeleteGame() {
        log.info("Execute testGameDAOTest shouldDeleteGame()");
        assertNotNull(gameDAO);
        Game expectedGame = gameDAO.findById(1).get();
        gameDAO.deleteById(expectedGame.getId());
        Optional<Game> deletedGame = gameDAO.findById(expectedGame.getId());
        assertTrue(deletedGame.isEmpty());
    }
}
