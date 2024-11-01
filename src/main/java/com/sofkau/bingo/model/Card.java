package com.sofkau.bingo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

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

    private List<Integer> numbers;
}
