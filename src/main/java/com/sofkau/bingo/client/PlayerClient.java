package com.sofkau.bingo.client;

import com.sofkau.bingo.dto.CreatePlayerDto;
import com.sofkau.bingo.dto.PlayerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@FeignClient(name = "user-service", url = "http://localhost:3000/api/v1")
public interface PlayerClient {
    @GetMapping("/user/{id}")
    PlayerDto getUser(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") String id);

    @PostMapping("/auth/register")
    PlayerDto registerUser(CreatePlayerDto createPlayerDtoPlayerDto);
}
