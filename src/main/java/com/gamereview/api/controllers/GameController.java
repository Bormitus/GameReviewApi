package com.gamereview.api.controllers;

import com.gamereview.api.dtos.GameDto;
import com.gamereview.api.dtos.GameResponseDto;
import com.gamereview.api.services.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameController {

    GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("game")
    public ResponseEntity<GameResponseDto> getGames(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
            ) {
        return new ResponseEntity<>(gameService.getAllGames(pageNumber,pageSize), HttpStatus.OK);
    }

    @GetMapping("game/{id}")
    public ResponseEntity<GameDto> getGame(@PathVariable("id") int id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @PostMapping("game/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GameDto> createGame(@RequestBody GameDto gameDto) {

        return new ResponseEntity<>(gameService.createGame(gameDto), HttpStatus.CREATED);
    }

    @PutMapping("game/{id}")
    public ResponseEntity<GameDto> updateGame(@RequestBody GameDto gameDto, @PathVariable("id") int gameId) {
        GameDto response = gameService.updateGame(gameDto,gameId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("game/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable("id") int gameId) {
        gameService.deleteGameById(gameId);
        return new ResponseEntity<>("Game deleted", HttpStatus.OK);
    }
}
