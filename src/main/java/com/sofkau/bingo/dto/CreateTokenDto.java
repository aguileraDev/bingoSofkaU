package com.sofkau.bingo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record CreateTokenDto(
        @NotNull
        @JsonAlias("token")
        Integer tokenCalled
) {
}
