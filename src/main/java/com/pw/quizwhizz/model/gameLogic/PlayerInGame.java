package com.pw.quizwhizz.model.gameLogic;

import com.pw.quizwhizz.model.gameLogic.exceptions.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.gameLogic.entity.Answer;
import lombok.Getter;

import java.util.List;

/**
 * Created by Karolina on 14.04.2017.
 */
// Ultimately PlayerInGame will be created based on the Player and Game retrieved from the DB in a REST API call
@Getter
public class PlayerInGame extends Player {
    private final Game game;
    private final boolean isOwner;

    public PlayerInGame(String name, Game game) {
        super(name);
        this.game = game;

        if(game.getPlayers().size() == 0) {
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
}
