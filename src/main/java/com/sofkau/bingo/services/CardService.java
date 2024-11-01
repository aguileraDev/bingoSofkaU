package com.sofkau.bingo.services;

import com.sofkau.bingo.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public class CardService {
    private final static Logger logger = LoggerFactory.getLogger(CardService.class);
    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


}
