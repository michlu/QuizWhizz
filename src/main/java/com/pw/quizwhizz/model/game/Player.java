package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by Karolina on 14.04.2017.
 */
// Ultimately PlayerInGame will be created based on the Player and Game retrieved from the DB in a REST API call

// Potrzebujemy jednak tabeli PlayerInGame z wartosciami game_id, user_id i boolem isOwner
// Przy startcie gry w kontrolerze bedzie sprawdzana tozsamosc gracza - tylko owner moze zaczac
// Jak rozwiazac kwestie dodania user_id, skoro PlayerInGame dziedziczy z Playera??

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Player {

    @Transient
    @Setter
    private String name;

    @Transient
    @Setter
    private long id;

    @Column(name = "player_xp")
    private int xp;

    @Column(name = "games_played")
    private int gamesPlayed;

    private Game game;
    private boolean isOwner;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, Game game) {
        this.name = name;
        this.game = game;

        if (game.getPlayers().size() == 0) {
            this.isOwner = true;
        } else {
            this.isOwner = false;
        }

        game.addPlayer(this);
    }

    public void startGame() {
        if (this.isOwner)
            game.start();
    }

    public void submitAnswers(List<Answer> answers) throws IllegalTimeOfAnswerSubmissionException {
        if (answers == null) {
            return;
        }

        game.evaluateAnswers(this, answers);
    }

    public void addXp(int xp) {
        this.xp += xp;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed += 1;
    }
}