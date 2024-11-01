package com.sofkau.bingo.repository;

import com.sofkau.bingo.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
