package com.pw.quizwhizz.dto.game;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by karol on 03.05.2017.
 */
@Data
@Embeddable
public class PlayerInGameKey implements Serializable{
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Column(name = "game_id", nullable = false)
    private long gameId;
}