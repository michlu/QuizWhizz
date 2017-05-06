package com.pw.quizwhizz.dto.game;

import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.model.game.GameState;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Created by Karolina on 30.04.2017.
 */
@Getter
@Component
public class GameDTOBuilder {
   private CategoryDTO category;
   private GameState gameState;
   private Instant startTime;

    public GameDTO build() {
       return new GameDTO(this);
    }

    public GameDTOBuilder withCategory(CategoryDTO category){
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
