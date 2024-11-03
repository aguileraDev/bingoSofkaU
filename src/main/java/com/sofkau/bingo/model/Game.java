package com.sofkau.bingo.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sofkau.bingo.dto.GameDto;
import jakarta.persistence.*;
import lombok.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Entity
@Table(name = "game")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Game implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean isActive;
    private Boolean isFinished;
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Card> cards = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Token> tokens = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Winner> winners = new ArrayList<>();

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
        this.id = gameDto.id();
        this.isActive = gameDto.isActive();
        this.isFinished = gameDto.isFinished();
        this.createdAt = gameDto.createdAt();
    }

}