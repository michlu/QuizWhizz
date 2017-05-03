package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.game.Answer;
import com.pw.quizwhizz.model.game.Player;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Karolina on 14.04.2017.
 */

@Getter
public class Score {
    private Player player;
    private int points;
    private boolean isHighest;

    protected Score(Player player) {
        this.player = player;
    }

    protected void evaluateAnswers(List<Answer> submittedAnswers) {
        for (int i = 0; i < submittedAnswers.size(); i++) {
            Answer answer = submittedAnswers.get(i);
            if (answer.isCorrect()) {
                points += 10;
            }
        }
        player.addXp(points);
        player.incrementGamesPlayed();
    }

    protected void markAsHighest() {
        isHighest = true;
        player.addXp(30);
    }
}
