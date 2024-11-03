package com.sofkau.bingo.repository;

import com.sofkau.bingo.model.Winner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Repository
public interface WinnerRepository extends JpaRepository<Winner, Long> {
}
