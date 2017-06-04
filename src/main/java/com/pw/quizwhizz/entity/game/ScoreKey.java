package com.pw.quizwhizz.entity.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Zlozony klucz glowny dla encji Score
 *
 * @author Karolina Prusaczyk
 */
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class ScoreKey implements Serializable {
    @Column(name = "game_id", nullable = false)
    private long gameId;

    @Column(name = "user_id", nullable = false)
    private long userId;
}
