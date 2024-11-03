package com.sofkau.bingo.dto;

import com.sofkau.bingo.model.Game;

import java.time.ZonedDateTime;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record GameDto(
        Long id,
        Boolean isActive,
        Boolean isFinished,
        ZonedDateTime createdAt
) {
    public GameDto(Game game) {
        this(game.getId(), game.getIsActive(), game.getIsFinished(), game.getCreatedAt());
    }
}
