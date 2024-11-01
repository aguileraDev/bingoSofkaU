package com.sofkau.bingo.services;


import com.sofkau.bingo.client.PlayerClient;
import com.sofkau.bingo.dto.CreatePlayerDto;
import com.sofkau.bingo.dto.PlayerDto;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

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
        return playerClient.getUser(token, id);
    }

    public PlayerDto registerPlayer(CreatePlayerDto createPlayerDto) {
        return playerClient.registerUser(createPlayerDto);
    }


}
