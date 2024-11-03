package com.sofkau.bingo.dto;

import com.sofkau.bingo.model.Winner;

import java.time.ZonedDateTime;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record WinnerDto(
        Long id,
        String player,
        ZonedDateTime finishTime
) {
    public WinnerDto(Winner winner){
        this(winner.getId(), winner.getPlayerId(), winner.getFinishTime());
    }
}
