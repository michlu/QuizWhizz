package com.pw.quizwhizz.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Created by Karolina on 20.03.2017.
 */
// ZMIANA PODEJSCIA
// Do zmapowania na Usera (zmniejszy liczbe kolejnych zawolan do bazy z kontrolera
// Do tabeli Usera dojda tylko informacje o XP i gamesPlayed

@Getter
@EqualsAndHashCode
@Embeddable
public class Player {

    @Transient
    private final String name;

    @Column(name = "player_xp")
    private int xp;

    @Column(name = "games_played")
    private int gamesPlayed;

    public Player(String name) {
        this.name = name;
    }

    void addXp(int xp) {
        this.xp += xp;
    }

    protected void incrementGamesPlayed() {
        this.gamesPlayed += 1;
    }
}
