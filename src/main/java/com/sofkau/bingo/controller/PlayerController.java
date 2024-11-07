package com.sofkau.bingo.controller;

import com.sofkau.bingo.dto.CreatePlayerDto;
import com.sofkau.bingo.dto.RegisterLoginDto;
import com.sofkau.bingo.services.PlayerService;
import com.sofkau.bingo.utility.http.Response;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author Manuel Aguilera / @aguileradev
 */
@RestController
@RequestMapping("/player")
@CrossOrigin(origins = "http://localhost:4200")
public class PlayerController {
    private final static Logger logger = LoggerFactory.getLogger(PlayerController.class);

    private final PlayerService playerService;
    private final Response response;

    @Autowired
    public PlayerController(PlayerService playerService, Response response) {
        this.playerService = playerService;
        this.response = response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getUser(@RequestHeader("Authorization") String authorizationHeader,
                                            @PathVariable("id") String id) {
        response.restart();
        response.data = playerService.getUser(authorizationHeader, id);
        response.error = false;
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Response> registerPlayer(@RequestBody @Valid CreatePlayerDto createPlayerDto) {
        response.restart();
        response.data = playerService.registerPlayer(createPlayerDto);
        response.error = false;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginPlayer(@RequestBody @Valid RegisterLoginDto registerLoginDto) {
        response.restart();
        response.data = playerService.loginPlayer(registerLoginDto);
        response.error = false;
        return ResponseEntity.ok(response);
    }
}
