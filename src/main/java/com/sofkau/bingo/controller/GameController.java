package com.sofkau.bingo.controller;

<<<<<<< HEAD
import com.sofkau.bingo.dto.CreatePlayerDto;
import com.sofkau.bingo.model.Game;
import com.sofkau.bingo.services.BingoService;
import com.sofkau.bingo.services.PlayerService;
=======
import com.sofkau.bingo.model.Game;
import com.sofkau.bingo.services.BingoService;
>>>>>>> a92153e2feac9850a5065757de8e56c4e867cb9e
import com.sofkau.bingo.utility.http.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@RestController
@RequestMapping("/game")
public class GameController {

    private final static Logger logger = LoggerFactory.getLogger(GameController.class);
    private final BingoService bingoService;
<<<<<<< HEAD
    private final PlayerService playerService;
    private Response response;

    @Autowired
    public GameController(BingoService bingoService, Response response, PlayerService playerService) {
        this.bingoService = bingoService;
        this.response = response;
        this.playerService = playerService;
=======
    private Response response;

    @Autowired
    public GameController(BingoService bingoService, Response response) {
        this.response = response;
        this.bingoService = bingoService;
>>>>>>> a92153e2feac9850a5065757de8e56c4e867cb9e
    }

    @PostMapping("/start")
    public ResponseEntity<Response> startGame() {
        response.restart();
        Game game = new Game(bingoService.startGame());
        URI uri = UriComponentsBuilder.fromUriString("/game/{id}").buildAndExpand(game.getGameId()).toUri();
        response.data = game;
        response.error = false;
        return ResponseEntity.created(uri).body(response);
    }

<<<<<<< HEAD
    @GetMapping("/player/{id}")
    public ResponseEntity<Response> getUser(@RequestHeader("Authorization") String authorizationHeader,
                                            @PathVariable("id") String id) {
        response.restart();
        response.data = playerService.getUser(authorizationHeader, id);
        response.error = false;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/player")
    public ResponseEntity<Response> registerPlayer(@RequestBody CreatePlayerDto createPlayerDto) {
        response.restart();
        response.data = playerService.registerPlayer(createPlayerDto);
        response.error = false;
        return ResponseEntity.ok(response);
    }
=======
>>>>>>> a92153e2feac9850a5065757de8e56c4e867cb9e

}
