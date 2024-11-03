package com.sofkau.bingo.repository;

import com.sofkau.bingo.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByGameId(Long gameId);
}
