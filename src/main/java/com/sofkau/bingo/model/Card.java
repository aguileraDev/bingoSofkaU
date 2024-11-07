package com.sofkau.bingo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sofkau.bingo.dto.CardDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Entity(name = "card")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String playerId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id",nullable = false)
    @JsonIgnore
    private Game game;


    @Column(name = "numbers", columnDefinition = "integer[]")
    private List<Integer> numbers = new ArrayList<>();

    public Card(CardDto cardDto){
        this.id = cardDto.id();
        this.playerId = cardDto.player();
        this.numbers = cardDto.numbers();
    }
    public Card(Game game, String player, List<Integer> allDataColumns) {
        this.playerId = player;
        this.game = game;
        this.numbers = new ArrayList<>(allDataColumns);

    }
}
