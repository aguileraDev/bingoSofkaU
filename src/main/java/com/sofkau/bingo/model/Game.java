package com.sofkau.bingo.model;

import com.sofkau.bingo.dto.GameDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
/**
 * @author Manuel Aguilera / @aguileradev
 */
@Entity
@Table(name = "game")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Game implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long gameId;

    @Column
    private Boolean isActive;
    private Boolean isFinished;
    private ZonedDateTime createdAt;

    public Game() {
        this.isActive = true;
        this.isFinished = false;

        try{
            this.createdAt = ZonedDateTime.now(ZoneId.of("America/Bogota"));
        }catch (DateTimeException e){
            logger.error("Error: bad zone datetime");
        }
    }

    public Game(GameDto gameDto) {
        this.gameId = gameDto.id();
        this.isActive = gameDto.isActive();
        this.isFinished = gameDto.isFinished();
        this.createdAt = gameDto.createdAt();
    }
}