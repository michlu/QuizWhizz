package com.pw.quizwhizz.dto.game;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "score")
public class ScoreDTO {
    @EmbeddedId
    private ScoreKey id;
    @Column
    private Integer points;
    @Column
    private boolean isHighest;
}
