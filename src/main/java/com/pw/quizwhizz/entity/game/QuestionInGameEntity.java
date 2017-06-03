package com.pw.quizwhizz.entity.game;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Encja QuestionrInGame
 * @author Karolina Prusaczyk
 */
@Entity
@Getter @Setter
@Table(name = "question_in_game")
public class QuestionInGameEntity {
    @EmbeddedId
    private QuestionInGameKey id;

    @Column
    private int sequence;
}
