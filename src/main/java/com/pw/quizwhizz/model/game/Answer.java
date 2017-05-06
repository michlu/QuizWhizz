package com.pw.quizwhizz.model.game;

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
public class Answer {

    private long id;
    private String answer;
    private boolean isCorrect;

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
    // GET wymagany dla Thymeleafa
    public boolean getIsCorrect() {
        return isCorrect;
    }
}
