package com.sofkau.bingo.dto;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record LoginDto(
        String id,
        String name,
        String email,
        Boolean isActive,
        String token
) {
}
