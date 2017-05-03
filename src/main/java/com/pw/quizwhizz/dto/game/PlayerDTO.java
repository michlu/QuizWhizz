package com.pw.quizwhizz.dto.game;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by karol on 03.05.2017.
 */
@Entity
@Data
@Table(name = "user")
public class PlayerDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String name;

    @Column(name = "player_xp")
    private int xp;

    @Column(name = "games_played")
    private int gamesPlayed;

}
