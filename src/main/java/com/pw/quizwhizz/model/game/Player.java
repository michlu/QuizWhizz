package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by Karolina on 14.04.2017.
 */

@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Player {
    private String name;
    private long id;
    private int xp;
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