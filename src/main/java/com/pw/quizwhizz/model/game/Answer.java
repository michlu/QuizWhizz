package com.pw.quizwhizz.model.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Karolina on 25.03.2017.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "answer")
@JsonIgnoreProperties
public class Answer {
    @Id
    @Column(name = "id_answer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer;
    @Column(nullable = false)
    @JsonProperty
    private boolean isCorrect;

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    // GET wymagany dla Thymeleafa
    public boolean getIsCorrect() {
        return isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
