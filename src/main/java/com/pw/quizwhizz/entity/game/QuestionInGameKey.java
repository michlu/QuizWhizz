package com.pw.quizwhizz.entity.game;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by karol on 03.05.2017.
 */
@Data
@Embeddable
public class QuestionInGameKey implements Serializable {
    @Column(name = "game_id", nullable = false)
    private long gameId;

    @Column(name = "question_id", nullable = false)
    private long questionId;
}