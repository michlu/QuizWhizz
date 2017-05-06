package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.annotation.UniqueCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Karolina on 26.03.2017.
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
