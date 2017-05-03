package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.category.Category;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Created by Karolina on 30.04.2017.
 */
@Getter
@Component
public class GameDTOBuilder {
   private Category category;
   private GameState gameState;
   private Instant startTime;

    public GameDTO build() {
       return new GameDTO(this);
    }

    public GameDTOBuilder withCategory(Category category){
        this.category = category;
        return this;
    }

    public GameDTOBuilder withCurrentState(GameState gameState) {
        this.gameState = gameState;
        return this;
    }

    public GameDTOBuilder withStartTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }
}
