package com.sofkau.bingo.repository;

import com.sofkau.bingo.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByIdAndIsActiveTrue(Long id);
}
