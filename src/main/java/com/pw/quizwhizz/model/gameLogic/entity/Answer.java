package com.pw.quizwhizz.model.gameLogic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Karolina on 25.03.2017.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "answer")
@JsonIgnoreProperties
public class Answer {
    @Id
    @Column(name = "id_answer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer;
    @Column(columnDefinition = "TINYINT DEFAULT FALSE", nullable = false)
    @JsonProperty
    private boolean isCorrect;

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
}
