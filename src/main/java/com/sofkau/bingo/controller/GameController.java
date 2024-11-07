package com.sofkau.bingo.controller;


import com.sofkau.bingo.dto.RegisterPlayerDto;
import com.sofkau.bingo.dto.CreateTokenDto;
import com.sofkau.bingo.dto.GameResponseDto;
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
@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<Response> startGame(@RequestBody @Valid RegisterPlayerDto registerPlayerDto) {
        response.restart();
        GameResponseDto game = bingoService.startGame(registerPlayerDto);
        URI uri = UriComponentsBuilder.fromUriString("/game/{id}").buildAndExpand(game.id()).toUri();
        response.data = game;
        response.error = false;
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("card/{id}")
    public ResponseEntity<Response> getCardboard(@PathVariable("id") Integer id) {
        response.restart();
        response.data = cardService.getCardboard(Long.valueOf(id));
        response.error = false;
        return ResponseEntity.ok(response);
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

    @PostMapping("/{id}/token")
    public ResponseEntity<Response> saveToken(@PathVariable("id") Integer id,
                                              @RequestBody @Valid CreateTokenDto createTokenDto) {

        response.restart();
        response.data = bingoService.saveToken(Long.valueOf(id), createTokenDto);
        response.error = false;
        URI uri = UriComponentsBuilder.fromUriString("/game/{id}/token").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}/all/token")
    public ResponseEntity<Response> getAllTokens(@PathVariable("id") Integer id) {
        response.restart();
        response.data = bingoService.getTokens(Long.valueOf(id));
        response.error = false;
        return ResponseEntity.ok(response);
    }

    @GetMapping("/token/{id}")
    public ResponseEntity<Response> getToken(@PathVariable("id") Integer id) {
        response.restart();
        response.data = bingoService.getToken(Long.valueOf(id));
        response.error = false;
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{id}/winner")
    public ResponseEntity<Response> saveWinner(@PathVariable("id") Integer id, @RequestBody @Valid RegisterPlayerDto registerPlayerDto) {
        bingoService.saveWinner(Long.valueOf(id), registerPlayerDto);
        return ResponseEntity.noContent().build();
    }


}
