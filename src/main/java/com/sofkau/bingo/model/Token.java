package com.sofkau.bingo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sofkau.bingo.dto.CreateTokenDto;
import com.sofkau.bingo.dto.TokenDto;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "token")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Token implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(Token.class);
    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer tokenCalled;
    private ZonedDateTime callTime;

    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private Game game;


    public Token(Game game, CreateTokenDto createTokenDto) {
        this.game = game;
        this.tokenCalled = createTokenDto.tokenCalled();

        try {
            this.callTime = ZonedDateTime.now(ZoneId.of("America/Bogota"));
        } catch (DateTimeException e) {
            logger.error("Error: bad datetime - token");
        }
    }

    public Token(TokenDto tokenDto){
        this.id = tokenDto.id();
        this.tokenCalled = tokenDto.token();
        this.callTime = tokenDto.callTime();


    }


}
