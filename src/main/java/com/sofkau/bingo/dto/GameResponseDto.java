package com.sofkau.bingo.dto;

import java.util.List;

/**
 *
 * @author Manuel Aguilera / @aguileradev
*/public record GameResponseDto(
        Long id,
        String player,
        List<Integer> numbers
) {
    public GameResponseDto(GameDto gameDto, CardDto cardDto){
        this(
                gameDto.id(),
                cardDto.player(),
                cardDto.numbers().stream().toList());
    }
}
