package com.pw.quizwhizz.entity.game;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by karol on 03.05.2017.
 */
@Entity
@Data
@Table(name = "user")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String name;

    @Column(name = "player_xp")
    private Integer xp;

    @Column(name = "games_played")
    private Integer gamesPlayed;

}
