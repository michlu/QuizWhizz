package com.pw.quizwhizz.dto.game;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by karol on 03.05.2017.
 */
@Getter @Setter
@Embeddable
public class ScoreKey implements Serializable {
    @Column(name = "game_id", nullable = false)
    private long gameId;
    @Column(name = "user_id", nullable = false)
    private long userId;
}
