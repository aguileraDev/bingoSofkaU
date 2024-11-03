package com.sofkau.bingo.dto;

import com.sofkau.bingo.model.Token;

import java.time.ZonedDateTime;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record TokenDto(
        Long id,
        Integer token,
        ZonedDateTime callTime
) {
    public TokenDto(Token token) {
        this(token.getId(), token.getTokenCalled(), token.getCallTime());
    }
}
