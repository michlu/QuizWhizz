package com.pw.quizwhizz.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Encja Answer
 * @author Michał Nowiński
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "answer")
@JsonIgnoreProperties
public class AnswerEntity {
    @Id
    @Column(name = "id_answer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer;
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private boolean isCorrect;


    public boolean getIsCorrect() {
        return isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}

