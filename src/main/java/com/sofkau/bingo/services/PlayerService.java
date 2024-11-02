package com.sofkau.bingo.services;


import com.sofkau.bingo.client.PlayerClient;
import com.sofkau.bingo.dto.CreatePlayerDto;
import com.sofkau.bingo.dto.LoginDto;
import com.sofkau.bingo.dto.RegisterLoginDto;
import com.sofkau.bingo.dto.PlayerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Manuel Aguilera / @aguileradev
 */

@Service
public class PlayerService {

    private final static Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerClient playerClient;

    @Autowired
    public PlayerService(PlayerClient playerClient) {
        this.playerClient = playerClient;
    }

    public PlayerDto getUser(String token, String id) {
        return playerClient.getPlayer(token, id);
    }

    public PlayerDto registerPlayer(CreatePlayerDto createPlayerDto) {
        return playerClient.registerPlayer(createPlayerDto);
    }

    public LoginDto loginPlayer(RegisterLoginDto registerLoginDto){
        return playerClient.loginPlayer(registerLoginDto);
    }


}
