package com.pw.quizwhizz.entity.game;

import lombok.Data;

import javax.persistence.*;

/**
 * Encja Player dostarczajaca informacji o graczu.
 * @author Karolina Prusaczyk
 */
@Entity
@Data
@Table(name = "user")
public class PlayerEntity {
    @Id
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String name;

    @Column(name = "player_xp")
    private Integer xp;

    @Column(name = "games_played")
    private Integer gamesPlayed;

}
