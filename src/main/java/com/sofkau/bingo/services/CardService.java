package com.sofkau.bingo.services;

import com.sofkau.bingo.dto.CardDto;
import com.sofkau.bingo.dto.RegisterPlayerDto;
import com.sofkau.bingo.model.Card;
import com.sofkau.bingo.model.Game;
import com.sofkau.bingo.repository.CardRepository;
import com.sofkau.bingo.repository.GameRepository;
import com.sofkau.bingo.utility.exceptions.NotFoundException;
import com.sofkau.bingo.utility.exceptions.RegisterException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Service
public class CardService {
    private final static Logger logger = LoggerFactory.getLogger(CardService.class);
    private final CardRepository cardRepository;
    private final GameRepository gameRepository;

    @Autowired
    public CardService(CardRepository cardRepository, GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.cardRepository = cardRepository;
    }

    @Transactional
    public CardDto createCard(Long id, RegisterPlayerDto registerPlayerDto) {
        Optional<Game> game;
        Optional<Card> card;
        try {
            game = Optional.of(gameRepository.findByIdAndIsActiveTrue(id)).orElseThrow();
        } catch (NoSuchElementException e) {
            String message = "Game with id " + id + " not found";
            throw new NotFoundException(message);

        }

        List<Integer> columnB = generateColumn(1, 15);
        List<Integer> columnI = generateColumn(16, 30);
        List<Integer> columnN = generateColumn(31, 45);
        List<Integer> columnG = generateColumn(46, 60);
        List<Integer> columnO = generateColumn(61, 75);

        List<Integer> allDataColumns = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            allDataColumns.add(columnB.get(i));
            allDataColumns.add(columnI.get(i));
            allDataColumns.add(columnN.get(i));
            allDataColumns.add(columnG.get(i));
            allDataColumns.add(columnO.get(i));
        }
        if (allDataColumns.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }

        logger.info("Create card numbers");

        try {
            card = Optional.of(cardRepository.save(new Card(game.get(), registerPlayerDto.playerId(), allDataColumns)));
        } catch (DataIntegrityViolationException e) {
            String message = "Create card failed";
            throw new RegisterException(message);
        }

        logger.info("Card saved");

        return new CardDto(card.get());
    }

    @Transactional
    public CardDto getCardboard(Long id){
        Optional<Card> card = Optional.of(cardRepository.findByGameId(id)).orElseThrow();

        return new CardDto(card.get());

    }

    public List<Integer> generateColumn(int numberMin, int numberMax) {
        HashSet<Integer> values = new HashSet<>();

        while (values.size() < 5) {
            int temp = (int) (Math.random() * (numberMax - numberMin + 1)) + numberMin;
            values.add(temp);
        }

        return values.stream().collect(Collectors.toList());
    }
}


