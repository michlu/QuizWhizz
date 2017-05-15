package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.game.Answer;
import com.pw.quizwhizz.model.game.Player;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Karolina on 14.04.2017.
 */

@Component
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Score {
    private Player player;
    private long gameId;
    private int points;
    private boolean isHighest;

    public Score(Player player) {
        this.player = player;
    }

    public Score(ScoreBuilder builder) {
        this.player = builder.getPlayer();
        this.gameId = builder.getGameId();
        this.points = builder.getPoints();
        this.isHighest = builder.isHighest();
    }

    protected void evaluateAnswers(List<Answer> submittedAnswers) {
        for (int i = 0; i < submittedAnswers.size(); i++) {
            Answer answer = submittedAnswers.get(i);
            if (answer.getIsCorrect()) {
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

