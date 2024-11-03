package com.sofkau.bingo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sofkau.bingo.dto.RegisterPlayerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Table(name = "winner")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Winner implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Winner.class);
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String playerId;
    private ZonedDateTime finishTime;
    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private Game game;

    public Winner(RegisterPlayerDto registerPlayerDto){
        this.playerId = registerPlayerDto.playerId();
        this.finishTime = setDateTimeWithZoneNow();

    }

    public Winner(Game game, RegisterPlayerDto registerPlayerDto){
        this.game = game;
        this.playerId = registerPlayerDto.playerId();
        this.finishTime = setDateTimeWithZoneNow();
    }

    private ZonedDateTime setDateTimeWithZoneNow(){
        try {
            return ZonedDateTime.now(ZoneId.of("America/Bogota"));
        }catch (DateTimeException e){
            String message = "set time failed - winner";
            logger.error(message);
            return ZonedDateTime.now();
        }
    }
}
