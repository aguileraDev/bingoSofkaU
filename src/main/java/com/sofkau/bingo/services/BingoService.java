package com.sofkau.bingo.services;

import com.sofkau.bingo.dto.GameDto;
import com.sofkau.bingo.repository.GameRepository;
import com.sofkau.bingo.utility.exceptions.RegisterException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.sofkau.bingo.model.Game;

import java.util.Optional;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Service
public class BingoService {

    private static final Logger logger = LoggerFactory.getLogger(BingoService.class);
    private final GameRepository gameRepository;

    @Autowired
    public BingoService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional
    public GameDto startGame() {
        logger.info("Start game");
        Optional<Game> gameDb;

        try{
            gameDb =Optional.of(gameRepository.save(new Game()));
        }catch (DataIntegrityViolationException e){
            String message = "Start game failed";
            throw new RegisterException(message);
        }

        return new GameDto(gameDb.get());
    }

}
