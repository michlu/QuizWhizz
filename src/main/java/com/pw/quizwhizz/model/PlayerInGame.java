package com.pw.quizwhizz.model;

import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.answer.Answer;
import com.pw.quizwhizz.model.player.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@NoArgsConstructor
public class PlayerInGame extends Player {
    private long id;
    private Game game;
    private boolean isOwner;

    public PlayerInGame(String name, Game game) {
        super(name);
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
}