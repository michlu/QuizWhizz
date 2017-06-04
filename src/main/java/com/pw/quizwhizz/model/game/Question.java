package com.pw.quizwhizz.model.game;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Klasa reprezentujaca pytanie o okreslonej kategorii.
 * Kazde pytanie sklada się z listy odpowiedzi.
 *
 * @author Karolina Prusaczyk
 * @see Answer
 * @see Category
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

