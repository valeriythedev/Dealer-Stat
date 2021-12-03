package com.leverx.project.repository;

import com.leverx.project.application.Application;
import com.leverx.project.model.GameObject;
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
public class GameObjectDAOTest {

    private final GameObjectDAO gameObjectDAO;

    @Autowired
    public GameObjectDAOTest(GameObjectDAO gameObjectDAO) {
        this.gameObjectDAO = gameObjectDAO;
    }

    @Test
    void shouldCreateGameObject() {
        log.info("Execute test GameObjectDAOTest shouldCreateGameObject()");
        assertNotNull(gameObjectDAO);
        GameObject expectedGameObject = new GameObject(null, "Dragon Lore");
        GameObject savedGameObject = gameObjectDAO.save(expectedGameObject);
        assertNotNull(savedGameObject);
        expectedGameObject.setId(savedGameObject.getId());
        Optional<GameObject> actualGameObject = gameObjectDAO.findById(expectedGameObject.getId());
        assertTrue(actualGameObject.isPresent());
        assertEquals(expectedGameObject, actualGameObject.get());
    }

    @Test
    void shouldUpdateGameObject() {
        log.info("Execute test GameObjectDAOTest shouldUpdateGameObject()");
        assertNotNull(gameObjectDAO);
        GameObject expectedGameObject = gameObjectDAO.findById(7).get();
        GameObject actualGameObject = new GameObject(7, "Knife");
        expectedGameObject.setTitle(actualGameObject.getTitle());
        gameObjectDAO.save(actualGameObject);
        actualGameObject = gameObjectDAO.findById(7).get();
        assertEquals(expectedGameObject, actualGameObject);
    }

    @Test
    void shouldFindAllGameObjectsById() {
        log.info("Execute test GameObjectDAOTest shouldFindAllGameObjectsById()");
        assertNotNull(gameObjectDAO);
        List<GameObject> gameObjectList = gameObjectDAO.findAllGameObjectsById(43);
        assertNotNull(gameObjectList);
    }

    @Test
    void shouldFindAll() {
        log.info("Execute test GameObjectDAOTest shouldFindAll()");
        assertNotNull(gameObjectDAO);
        List<GameObject> gameObjectList = gameObjectDAO.findAll();
        assertNotNull(gameObjectList);
    }

    @Test
    void shouldDeleteGameObject() {
        log.info("Execute test GameObjectDAOTest shouldDeleteGameObject()");
        assertNotNull(gameObjectDAO);
        GameObject expectedGameObject = gameObjectDAO.findById(7).get();
        gameObjectDAO.deleteById(expectedGameObject.getId());
        Optional<GameObject> deletedGameObject = gameObjectDAO.findById(expectedGameObject.getId());
        assertTrue(deletedGameObject.isEmpty());
    }
}
