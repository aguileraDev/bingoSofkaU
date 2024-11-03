package com.sofkau.bingo.controller;


import com.sofkau.bingo.dto.CreateCardDto;
import com.sofkau.bingo.dto.CreatePlayerDto;
import com.sofkau.bingo.dto.RegisterLoginDto;
import com.sofkau.bingo.model.Game;
import com.sofkau.bingo.services.BingoService;
import com.sofkau.bingo.services.CardService;
import com.sofkau.bingo.services.PlayerService;

import com.sofkau.bingo.utility.http.Response;

import jakarta.validation.Valid;
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
    private final CardService cardService;
    private final PlayerService playerService;
    private Response response;

    @Autowired
    public GameController(BingoService bingoService, Response response, PlayerService playerService, CardService cardService) {
        this.bingoService = bingoService;
        this.response = response;
        this.playerService = playerService;
        this.cardService = cardService;
    }

    @PostMapping("/start")
    public ResponseEntity<Response> startGame() {
        response.restart();
        Game game = new Game(bingoService.startGame());
        URI uri = UriComponentsBuilder.fromUriString("/game/{id}").buildAndExpand(game.getId()).toUri();
        response.data = game;
        response.error = false;
        return ResponseEntity.created(uri).body(response);
    }


    @GetMapping("/player/{id}")
    public ResponseEntity<Response> getUser(@RequestHeader("Authorization") String authorizationHeader,
                                            @PathVariable("id") String id) {
        response.restart();
        response.data = playerService.getUser(authorizationHeader, id);
        response.error = false;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/player")
    public ResponseEntity<Response> registerPlayer(@RequestBody @Valid CreatePlayerDto createPlayerDto) {
        response.restart();
        response.data = playerService.registerPlayer(createPlayerDto);
        response.error = false;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/player/login")
    public ResponseEntity<Response> loginPlayer(@RequestBody @Valid RegisterLoginDto registerLoginDto) {
        response.restart();
        response.data = playerService.loginPlayer(registerLoginDto);
        response.error = false;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cardboard/{id}")
    public ResponseEntity<Response> createCard(@PathVariable("id") Integer id, @RequestBody @Valid CreateCardDto createCardDto) {
        response.restart();
        response.data = cardService.createCard(id, createCardDto);
        response.error = false;
        return ResponseEntity.ok(response);
    }

}
