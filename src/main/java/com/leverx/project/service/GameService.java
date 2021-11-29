package com.leverx.project.service;

import com.leverx.project.model.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {

    Game create(Game game);

    Game update(Integer id, Game game);

    Optional<Game> getById(Integer id);

    void delete(Integer id);

    List<Game> getAll();
}
