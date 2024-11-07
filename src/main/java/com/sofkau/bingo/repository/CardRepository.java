package com.sofkau.bingo.repository;

import com.sofkau.bingo.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByGameId(Long id);
}
