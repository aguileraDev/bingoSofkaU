package com.sofkau.bingo.controller;


import com.sofkau.bingo.dto.CreateCardDto;
import com.sofkau.bingo.dto.GameResponseDto;
import com.sofkau.bingo.model.Game;
import com.sofkau.bingo.services.BingoService;
import com.sofkau.bingo.services.CardService;

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
    private Response response;

    @Autowired
    public GameController(BingoService bingoService, Response response, CardService cardService) {
        this.bingoService = bingoService;
        this.response = response;
        this.cardService = cardService;
    }

    @PostMapping("/start")
    public ResponseEntity<Response> startGame(@RequestBody @Valid CreateCardDto createCardDto) {
        response.restart();
        GameResponseDto game = bingoService.startGame(createCardDto);
        URI uri = UriComponentsBuilder.fromUriString("/game/{id}").buildAndExpand(game.id()).toUri();
        response.data = game;
        response.error = false;
        return ResponseEntity.created(uri).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getGame(@PathVariable("id") Integer id) {
        response.restart();
        response.data = bingoService.getGame(Long.valueOf(id));
        response.error = false;
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> finishGame(@PathVariable("id") Integer id) {
        bingoService.finishGame(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }



}
