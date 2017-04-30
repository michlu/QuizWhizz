package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.category.Category;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Created by Karolina on 30.04.2017.
 */
@Component
public class GameDTOBuilder {
   private GameDTO game;

    public GameDTO build() {
       return game;
    }

    public GameDTOBuilder withCategory(Category category){
        game.setCategory(category);
        return this;
    }

    public GameDTOBuilder withCurrentState(GameState state) {
        game.setCurrentState(state);
        return this;
    }

    public GameDTOBuilder withStartTime(Instant startTime) {
        game.setStartTime(startTime);
        return this;
    }
}
