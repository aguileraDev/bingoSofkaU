package com.sofkau.bingo.dto;

import com.sofkau.bingo.model.Card;
import com.sofkau.bingo.model.Game;

import java.util.List;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record CardDto(
    Long id,
    Game game,
    String player,
    List<Integer> numbers
) {

    public CardDto(Card card) {
        this(card.getId(), card.getGame(), card.getPlayerId(), card.getNumbers());
    }
}
