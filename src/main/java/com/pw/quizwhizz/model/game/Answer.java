package com.pw.quizwhizz.model.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/**
 * Klasa wykorzystywana w grze jako element pytania.
 * Kazda instancja klasy Answer zawiera informację o poprawnosci odpowiedzi na pytanie lub jej braku.
 *
 * @author Karolina Prusaczyk
 */
@Data
@NoArgsConstructor
public class Answer {

    private long id;
    private String answer;
    private boolean isCorrect;

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
    public boolean getIsCorrect() {
        return isCorrect;
    }
}
