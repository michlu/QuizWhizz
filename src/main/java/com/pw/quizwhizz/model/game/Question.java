package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.game.Answer;
import com.pw.quizwhizz.model.game.Category;
import lombok.*;
import javax.persistence.*;
import java.util.List;

/**
 * Created by Karolina on 24.03.2017.
 */
@Data
@NoArgsConstructor
public class Question {
    private long id;
    private Category category;
    private String question;
    private List<Answer> answers;

    public Question(String question, Category category, List<Answer> answers) {
        this.category = category;
        this.question = question;
        this.answers = answers;
    }
}

