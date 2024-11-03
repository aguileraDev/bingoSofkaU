package com.sofkau.bingo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne
    @JoinColumn(name = "game_id",nullable = false)
    private Game game;


    @Column(name = "numbers", columnDefinition = "integer[]")
    private List<Integer> numbers = new ArrayList<>();

    public Card(Game game, String player, List<Integer> allDataColumns) {
        this.playerId = player;
        this.game = game;
        this.numbers = new ArrayList<>(allDataColumns);

    }
}
