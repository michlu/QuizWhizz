package com.pw.quizwhizz.model.game;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa dostarczajaca informacji o kategorii, w ktorej mozna utworzyc grę.
 *
 * @author Karolina Prusaczyk
 */
@Data
@NoArgsConstructor
public class Category {
    private long id;
    private String name;
    private String description;
    private String urlImage = "";

    public Category(String name) {
        this.name = name;
    }
}
