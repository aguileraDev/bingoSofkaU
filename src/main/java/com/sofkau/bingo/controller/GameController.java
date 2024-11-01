package com.sofkau.bingo.controller;

import com.sofkau.bingo.model.Game;
import com.sofkau.bingo.services.BingoService;
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
    private Response response;

    @Autowired
    public GameController(BingoService bingoService, Response response) {
        this.response = response;
        this.bingoService = bingoService;
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


}
