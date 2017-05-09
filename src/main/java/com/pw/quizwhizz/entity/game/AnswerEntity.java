package com.pw.quizwhizz.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Column(nullable = false)
    @JsonProperty
    private boolean isCorrect;


    public boolean getIsCorrect() {
        return isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}

