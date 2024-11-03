package com.sofkau.bingo.services;

import com.sofkau.bingo.dto.*;
import com.sofkau.bingo.model.Token;
import com.sofkau.bingo.model.Winner;
import com.sofkau.bingo.repository.GameRepository;
import com.sofkau.bingo.repository.TokenRepository;
import com.sofkau.bingo.repository.WinnerRepository;
import com.sofkau.bingo.utility.exceptions.RegisterException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.sofkau.bingo.model.Game;

import java.util.List;
import java.util.Optional;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Service
public class BingoService {

    private static final Logger logger = LoggerFactory.getLogger(BingoService.class);
    private final GameRepository gameRepository;
    private final CardService cardService;
    private final TokenRepository tokenRepository;
    private final WinnerRepository winnerRepository;

    @Autowired
    public BingoService(GameRepository gameRepository, CardService cardService,
                        TokenRepository tokenRepository, WinnerRepository winnerRepository) {
        this.gameRepository = gameRepository;
        this.cardService = cardService;
        this.tokenRepository = tokenRepository;
        this.winnerRepository = winnerRepository;
    }

    @Transactional
    public GameResponseDto startGame(RegisterPlayerDto registerPlayerDto) {
        logger.info("Start game");
        Optional<Game> gameDb;

        try{
            gameDb =Optional.of(gameRepository.save(new Game()));
        }catch (DataIntegrityViolationException e){
            String message = "Start game failed";
            throw new RegisterException(message);
        }

        var cardDto = cardService.createCard(gameDb.get().getId(), registerPlayerDto);

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

    @Transactional
    public TokenDto saveToken(Long id, CreateTokenDto createTokenDto) {
        logger.info("Saving token");

        var gameDto = getGame(id);

        Optional<Token> tokenDb;
        try{
            tokenDb = Optional.of(tokenRepository.save(new Token(new Game(gameDto), createTokenDto)));
        }catch (DataIntegrityViolationException e){
            String message = "Saving token failed";
            throw new RegisterException(message);
        }
        return new TokenDto(tokenDb.get());
    }

    @Transactional
    public List<TokenDto> getTokens(Long id) {
        logger.info("Get tokens");
        return tokenRepository.findAllByGameId(id).stream().map(TokenDto::new).toList();
    }

    @Transactional
    public WinnerDto saveWinner(Long id, RegisterPlayerDto registerPlayerDto){
        logger.info("Saving winner");
        GameDto gameDto = getGame(id);

        Optional<Winner> winnerDb;

        try{
            winnerDb = Optional.of(winnerRepository.save(new Winner(new Game(gameDto), registerPlayerDto)));
        }catch (DataIntegrityViolationException e){
            String message = "Saving winner failed";
            throw new RegisterException(message);
        }
        return new WinnerDto(winnerDb.get());
    }
}
