package com.leverx.project.rest.admin;

import com.leverx.project.model.Game;
import com.leverx.project.service.GameService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/admin/games/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class AdminGameController {

    private final GameService gameService;

    @Autowired
    public AdminGameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Game> create(@RequestBody Game game) {

        if(game == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }

        gameService.create(game);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Game> update(@PathVariable("id") Integer id, @RequestBody Game game) {

        List<Game> gameList = gameService.getAll();

        if(gameList.stream().noneMatch(p -> p.getId().equals(id))) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        Game updatedGame = gameService.update(id, game);

        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {

        List<Game> gameList = gameService.getAll();

        if(gameList.stream().noneMatch(p -> p.getId().equals(id))) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        gameService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Game>> getById(@PathVariable("id") Integer id) {

        List<Game> gameList = gameService.getAll();

        if(gameList.stream().noneMatch(p -> p.getId().equals(id))) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        Optional<Game> optionalGame = gameService.getById(id);

        return new ResponseEntity<>(optionalGame, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Game>> getAll() {
        List<Game> gameList = gameService.getAll();
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }
}
