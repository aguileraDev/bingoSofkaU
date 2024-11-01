package com.sofkau.bingo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record CreatePlayerDto(
        @NotBlank
        String name,
        @NotBlank
        String lastname,
        @Email
        String email,
        @NotBlank
        String password
) {
}
