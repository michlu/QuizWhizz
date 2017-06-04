package com.pw.quizwhizz.entity.game;

import com.pw.quizwhizz.model.game.GameState;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Implementacja wzorca Builder dla encji Game, wykorzystywana w serwisie gry.
 *
 * @author Karolina Prusaczyk
 * @see com.pw.quizwhizz.service.impl.GameServiceImpl
 */
@Getter
@Component
public class GameEntityBuilder {
   private CategoryEntity category;
   private GameState gameState;
   private Instant startTime;

    public GameEntity build() {
       return new GameEntity(this);
    }

    public GameEntityBuilder withCategory(CategoryEntity category){
        this.category = category;
        return this;
    }

    public GameEntityBuilder withCurrentState(GameState gameState) {
        this.gameState = gameState;
        return this;
    }

    public GameEntityBuilder withStartTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }
}
