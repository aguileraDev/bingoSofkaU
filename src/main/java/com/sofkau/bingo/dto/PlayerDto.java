package com.sofkau.bingo.dto;

import java.time.ZonedDateTime;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record PlayerDto(
        String id,
        String name,
        String lastname,
        String email,
        Boolean isActive,
        String registerDate) {
}
