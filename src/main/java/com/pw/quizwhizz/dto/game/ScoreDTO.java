package com.pw.quizwhizz.dto.game;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by karol on 03.05.2017.
 */
@Getter
@Setter
@Entity
@Table(name = "score")
public class ScoreDTO {
    @EmbeddedId
    private ScoreKey id;
    @Column
    private int points;
    @Column
    private boolean isHighest;
}
