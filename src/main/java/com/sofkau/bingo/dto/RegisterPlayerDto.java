package com.sofkau.bingo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record RegisterPlayerDto(
        @NotBlank
        @JsonAlias("player")
        String playerId
) {
}
