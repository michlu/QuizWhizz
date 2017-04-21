package com.pw.quizwhizz.model.gameLogic;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by Karolina on 20.03.2017.
 */
@Getter @EqualsAndHashCode
public class Player {
    private final String name;
    private int xp;
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
