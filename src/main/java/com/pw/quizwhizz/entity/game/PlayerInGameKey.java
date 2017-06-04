package com.pw.quizwhizz.entity.game;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Zlozony klucz glowny dla encji PlayerInGame
 *
 * @author Karolina Prusaczyk
 */
@Data
@Embeddable
public class PlayerInGameKey implements Serializable {
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "game_id", nullable = false)
    private long gameId;
}