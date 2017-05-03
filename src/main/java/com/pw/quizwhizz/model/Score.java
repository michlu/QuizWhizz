package com.pw.quizwhizz.model;

import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.answer.Answer;
import com.pw.quizwhizz.model.player.Player;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Karolina on 14.04.2017.
 */
@Entity
@Getter
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name="user_id")
    private long user_id;
    @Column
    private int points;
    @Column
    private boolean isHighest;

    @Transient
    private Player player;

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
