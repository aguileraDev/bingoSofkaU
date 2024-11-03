package com.sofkau.bingo.services;

import com.sofkau.bingo.dto.CreateCardDto;
import com.sofkau.bingo.dto.GameDto;
import com.sofkau.bingo.dto.GameResponseDto;
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
    private final CardService cardService;

    @Autowired
    public BingoService(GameRepository gameRepository, CardService cardService) {
        this.gameRepository = gameRepository;
        this.cardService = cardService;
    }

    @Transactional
    public GameResponseDto startGame(CreateCardDto createCardDto) {
        logger.info("Start game");
        Optional<Game> gameDb;

        try{
            gameDb =Optional.of(gameRepository.save(new Game()));
        }catch (DataIntegrityViolationException e){
            String message = "Start game failed";
            throw new RegisterException(message);
        }

        var cardDto = cardService.createCard(gameDb.get().getId(), createCardDto);

        return new GameResponseDto(new GameDto(gameDb.get()), cardDto);
    }

    @Transactional
    public GameDto getGame(Long id) {
        logger.info("Get game");
        Optional<Game> gameDb;
        try{
            gameDb = Optional.of(gameRepository.findById(id).orElseThrow());
        }catch (DataIntegrityViolationException e){
            String message = "Get game failed";
            throw new RegisterException(message);
        }
        return new GameDto(gameDb.get());
    }

    @Transactional
    public void finishGame(Long id) {
        logger.info("Finishing game with id " + id);
        Game gameDb = new Game(getGame(id));
        gameDb.setIsFinished(true);
        gameDb.setIsActive(false);
        gameRepository.save(gameDb);

    }


}
