package com.pw.quizwhizz.model;

import com.pw.quizwhizz.model.account.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * Created by Karolina on 20.03.2017.
 */
// ZMIANA PODEJSCIA
// Do zmapowania na Usera (zmniejszy liczbe kolejnych zawolan do bazy z kontrolera
// Do tabeli Usera dojda tylko informacje o XP i gamesPlayed

@Getter
@EqualsAndHashCode
@Entity
@Table(name = "player")
public class Player {
    @Id
    @Column(name = "player_id")
    Long id;

    @MapsId
    @OneToOne(mappedBy = "player")
    @JoinColumn(name = "user_id")   //same name as id @Column
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private final String name;

    @Column(name = "player_xp")
    private int xp;

    @Column(name = "games_played")
    private int gamesPlayed;

    public Player(String name) {
        this.name = name;
    }

    protected void addXp(int xp) {
        this.xp += xp;
    }

    protected void incrementGamesPlayed() {
        this.gamesPlayed += 1;
    }
}
